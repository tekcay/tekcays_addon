package tekcays_addon.gtapi.worldgen;

import com.google.gson.JsonObject;
import gregtech.api.util.FileUtility;
import gregtech.api.worldgen.config.OreDepositDefinition;
import gregtech.api.worldgen.config.WorldGenRegistry;
import gregtech.api.worldgen.generator.WorldGeneratorImpl;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import tekcays_addon.gtapi.utils.TKCYALog;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

import static tekcays_addon.api.config.Paths.*;
import static tekcays_addon.gtapi.consts.DepositValues.FLUID;
import static tekcays_addon.gtapi.consts.DepositValues.VEIN;
import static tekcays_addon.gtapi.consts.TKCYAValues.DIMENSIONS;


public class TKCYAWorldGenRegistry {

    public static final TKCYAWorldGenRegistry INSTANCE = new TKCYAWorldGenRegistry();

    /**
     * The Path for representing the vein folder in the vein folder in the assets folder in the Gregtech resources folder in the jar
     */
    private static Path oreVeinJarRootPath;

    /**
     * The Path for representing the fluid folder in the vein folder in the assets folder in the Gregtech resources folder in the jar
     */
    private static Path fluidDepositJarRootPath;

    private static final Map<Path, List<Path>> oreVeinsToAdd = new HashMap<>();
    private static final Map<Path, List<Path>> fluidVeinsToAdd = new HashMap<>();

    private final List<FluidDepositDefinition> fluidDepositDefinitions = new ArrayList<>();
    private final List<OreDepositDefinition> oreDepositDefinitions = new ArrayList<>();
    private static final Path DUMMY_FILE_PATH = GT_CONFIG_WORLDGEN_PATH.resolve("deleteToReset");

    private TKCYAWorldGenRegistry() {

    }

    public void initializeRegistry() {
        TKCYALog.logger.info("Initializing ore generation registry...");
        GameRegistry.registerWorldGenerator(WorldGeneratorImpl.INSTANCE, 1);
        MinecraftForge.ORE_GEN_BUS.register(WorldGeneratorImpl.class);
    }

    public static List<FluidDepositDefinition> getFluidDeposits() {
        return Collections.unmodifiableList(INSTANCE.fluidDepositDefinitions);
    }

    public static List<OreDepositDefinition> getOreDeposit() {
        return Collections.unmodifiableList(INSTANCE.oreDepositDefinitions);
    }

    public boolean doesDummyFileExist() {
        return Files.exists(DUMMY_FILE_PATH);
    }

    public void createDummyFile() throws IOException {
        Files.createDirectories(DUMMY_FILE_PATH.getParent());
        Files.createFile(DUMMY_FILE_PATH);
    }

    private void registerDeposits() {
        oreVeinsToAdd.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList())
                .forEach(this::registerOreDeposit);

        fluidVeinsToAdd.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList())
                .forEach(this::registerFluidDeposit);
    }

    private void registerFluidDeposit(Path fluidDepositFile) {

        Path depositPath = GT_CONFIG_FLUID_DEPOSIT_PATH.resolve(fluidDepositFile);

        JsonObject element = FileUtility.tryExtractFromFile(depositPath);
        try {
            // Creates the deposit definition and initializes various components based on the json entries in the file
            FluidDepositDefinition deposit = new FluidDepositDefinition(fluidDepositFile.getFileName().toString());
            deposit.initializeFromConfig(element);
            // Adds the registered definition to the list of all registered definitions
            fluidDepositDefinitions.add(deposit);
        } catch (
                RuntimeException exception) {
            TKCYALog.logger.error("Failed to parse worldgen definition {} on path {}", fluidDepositFile.getFileName().toString(), fluidDepositFile, exception);
        }
    }

    private void registerOreDeposit(Path oreDepositFile) {

        Path depositPath = GT_CONFIG_ORE_DEPOSIT_PATH.resolve(oreDepositFile);

        JsonObject element = FileUtility.tryExtractFromFile(depositPath);
        try {
            // Creates the deposit definition and initializes various components based on the json entries in the file
            OreDepositDefinition deposit = new OreDepositDefinition(oreDepositFile.getFileName().toString());
            deposit.initializeFromConfig(element);
            // Adds the registered definition to the list of all registered definitions
            oreDepositDefinitions.add(deposit);
        } catch (
                RuntimeException exception) {
            TKCYALog.logger.error("Failed to parse worldgen definition {} on path {}", oreDepositFile.getFileName().toString(), oreDepositFile, exception);
        }
    }

    public void removeAndAddVeins() throws IOException {

        TKCYALog.logger.info("Vein Size Before Addition: " + WorldGenRegistry.getOreDeposits().size());
        TKCYALog.logger.info("Fluid Vein Size Before Addition: " + WorldGenRegistry.getBedrockVeinDeposits().size());

        this.removeAllVeins(VEIN);
        this.removeAllVeins(FLUID);

        setPathAndExtractDefinitions();

        //TKCYAWorldGenRegistry.INSTANCE.reinitializeRegisteredVeins();

        TKCYAWorldGenRegistry.INSTANCE.registerDeposits();

        TKCYALog.logger.info("Vein Size After Addition: " + WorldGenRegistry.getOreDeposits().size());
        TKCYALog.logger.info("Fluid Vein Size After Addition: " + WorldGenRegistry.getBedrockVeinDeposits().size());
    }

    public void removeAllVeins(String veinType) {
        DIMENSIONS.forEach(dimension -> {
            try {
                FileUtils.cleanDirectory(new File(GT_WORLD_GEN_ROOT_PATH.resolve(veinType).resolve(dimension).toUri()));
                TKCYALog.logger.info("{} removed", dimension);
            } catch (IOException | IllegalArgumentException e) {
                TKCYALog.logger.info("Directory {} already removed", dimension);
            }
        });
    }

    private static void setJarRootPath(FileSystem zipFileSystem) throws URISyntaxException, IOException {

        URI sampleUri = TKCYAWorldGenRegistry.class.getResource("/assets/tkcya/.tkcyaassetsroot").toURI();

        if (sampleUri.getScheme().equals("jar") || sampleUri.getScheme().equals("zip")) {
            zipFileSystem = FileSystems.newFileSystem(sampleUri, Collections.emptyMap());
            oreVeinJarRootPath = zipFileSystem.getPath(TKCYA_JAR_ORE_DEPOSIT_PATH);
            fluidDepositJarRootPath = zipFileSystem.getPath(TKCYA_JAR_FLUID_DEPOSIT_PATH);
        } else if (sampleUri.getScheme().equals("file")) {
            oreVeinJarRootPath = Paths.get(TKCYAWorldGenRegistry.class.getResource(TKCYA_JAR_ORE_DEPOSIT_PATH).toURI());
            fluidDepositJarRootPath = Paths.get(TKCYAWorldGenRegistry.class.getResource(TKCYA_JAR_FLUID_DEPOSIT_PATH).toURI());
        } else {
            throw new IllegalStateException("Unable to locate absolute path to TKCYA worldgen root directory: " + sampleUri);
        }
    }

    private static void setPathAndExtractDefinitions() {
        FileSystem zipFileSystem = null;

        try {
            setJarRootPath(zipFileSystem);

            extractAndCopy(oreVeinJarRootPath, GT_CONFIG_ORE_DEPOSIT_PATH, oreVeinsToAdd);
            extractAndCopy(fluidDepositJarRootPath, GT_CONFIG_FLUID_DEPOSIT_PATH, fluidVeinsToAdd);

        } catch (URISyntaxException | IOException impossible) {
            //this is impossible, since getResource always returns valid URI
            throw new RuntimeException(impossible);
        } finally {
            IOUtils.closeQuietly(zipFileSystem);
        }
    }

    private static List<Path> getJarPathList(Path assetPath) throws IOException {
        return Files.walk(assetPath)
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());
    }

    private static void extractAndCopy(Path jarRootPath, Path gtConfigPath, Map<Path, List<Path>> worldGenMap) throws RuntimeException, IOException {
        TKCYALog.logger.info("Attempting retrieval of standard TKCYA worldgen definitions from {} to {}",
                jarRootPath, gtConfigPath);

        List<Path> jarFiles = getJarPathList(jarRootPath);

        if (!Files.exists(gtConfigPath)) {
            TKCYALog.logger.info("Directory {} did not exist so it was created", gtConfigPath);
            Files.createDirectories(gtConfigPath);
        }
        TKCYALog.logger.info("Directory {} exists", gtConfigPath);

        jarFiles.forEach(jarFile -> {
            worldGenMap.computeIfAbsent(jarFile.getParent(), k -> new ArrayList<>());

            Path name = jarRootPath.getParent().relativize(jarFile);
            name = getActualVeinName(name);
            worldGenMap.get(jarFile.getParent()).add(name);

            try {
                Files.createDirectories(gtConfigPath.resolve(name.getParent()));
                Files.copy(jarFile, gtConfigPath.resolve(name), StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        TKCYALog.logger.info("Retrieved {} builtin TKCYA worldgen definitions", jarFiles.size());
    }

    @Nonnull
    private static Path getActualVeinName(@Nonnull Path path) {

        //String separator = FileSystems.getDefault().getSeparator(); !!!! Works on Linux, on Windows it returns <\> !!!!

        String separator = System.getProperty("os.name").contains("Windows") ? "/" : FileSystems.getDefault().getSeparator();
        String[] split = path.toString().split("/");

        StringBuilder stringBuilder = new StringBuilder();

        Arrays.asList(split).forEach(part -> stringBuilder.append(part).append(separator));

        return Paths.get(stringBuilder.toString());

    }
}
