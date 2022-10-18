package tekcays_addon.api.worldgen;


import gregtech.api.GTValues;
import gregtech.api.worldgen.config.BedrockFluidDepositDefinition;
import gregtech.api.worldgen.config.OreDepositDefinition;
import gregtech.api.worldgen.config.WorldGenRegistry;
import net.minecraftforge.fml.common.Loader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import tekcays_addon.api.utils.TKCYALog;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;


/////////////////////////////////////////////////////////
// Thanks to Tech22 https://github.com/GregTechCEu/gregicality-multiblocks/blob/worldgen-example/src/main/java/gregicality/machines/api/worldgen/GCYMWorldGenRegistry.java
//////////////////////////////////////////////////////////

public class TKCYAWorldGenRegistry {

    public static final TKCYAWorldGenRegistry INSTANCE = new TKCYAWorldGenRegistry();

    private final Map<Path, List<String>> oreVeinsToAdd = new HashMap<>();
    private final Map<Path, List<String>> fluidVeinsToAdd = new HashMap<>();

    private TKCYAWorldGenRegistry() {

    }

    public void addRemoveVeins() throws IOException {
        Path configPath = Loader.instance().getConfigDir().toPath().resolve(GTValues.MODID);
        // The folder where worldgen definitions are stored
        Path worldgenRootPath = configPath.resolve("worldgen");

        // The folder where tkcya worldgen definitions are stored
        Path tkcyaWorldgenRootPath = worldgenRootPath.resolve("tkcya");

        // The folder where all physical veins are stored
        Path veinPath = tkcyaWorldgenRootPath.resolve("vein");
        if (!Files.exists(veinPath))
            Files.createDirectories(veinPath);

        // The folder where all bedrock fluid veins are stored
        Path bedrockVeinPath = tkcyaWorldgenRootPath.resolve("fluid");
        if (!Files.exists(bedrockVeinPath))
            Files.createDirectories(bedrockVeinPath);

        // Retrieve the defaults from the mod jar
        extractJarVeinDefinitions(configPath, veinPath, oreVeinsToAdd);
        extractJarVeinDefinitions(configPath, bedrockVeinPath, fluidVeinsToAdd);

        TKCYALog.logger.info("Vein Size Before Addition: " + WorldGenRegistry.getOreDeposits().size());
        TKCYALog.logger.info("Fluid Vein Size Before Addition: " + WorldGenRegistry.getBedrockVeinDeposits().size());
        addVeins();
        //addFluidVeins();  //TODO Returns NullPointer

        try {
            WorldGenRegistry.INSTANCE.reinitializeRegisteredVeins();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TKCYALog.logger.info("Vein Size After Addition: " + WorldGenRegistry.getOreDeposits().size());
        TKCYALog.logger.info("Fluid Vein Size After Addition: " + WorldGenRegistry.getBedrockVeinDeposits().size());

        TKCYALog.logger.info("Vein Size Before Removals: " + WorldGenRegistry.getOreDeposits().size());
        TKCYALog.logger.info("Fluid Vein Size Before Removals: " + WorldGenRegistry.getBedrockVeinDeposits().size());

        //removeFluidVeins();


        try {
            WorldGenRegistry.INSTANCE.reinitializeRegisteredVeins();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        TKCYALog.logger.info("Vein Size After Removals: " + WorldGenRegistry.getOreDeposits().size());
//        TKCYALog.logger.info("Fluid Vein Size After Removals: " + WorldGenRegistry.getBedrockVeinDeposits().size());
    }

    public void addVeins() {
        for (List<String> folder : oreVeinsToAdd.values()) {
            for (String vein : folder) {
                WorldGenRegistry.INSTANCE.addVeinDefinitions(new OreDepositDefinition(vein));
            }
        }
    }

    public void addFluidVeins() {
        for (List<String> folder : fluidVeinsToAdd.values()) {
            for (String vein : folder) {
                WorldGenRegistry.INSTANCE.addVeinDefinitions(new BedrockFluidDepositDefinition(vein));
            }
        }
    }

    public void removeVeins(String dimension) throws IOException {

        Path path = Loader.instance().getConfigDir().toPath().resolve(GTValues.MODID).resolve("worldgen").resolve("vein").resolve(dimension);

        Files.walk(path)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);

    }

    public void removeFluidVeins() {
        for (int i = 0; i < WorldGenRegistry.getBedrockVeinDeposits().size(); i++) {
            BedrockFluidDepositDefinition definition = WorldGenRegistry.getBedrockVeinDeposits().get(i);

            /*
            if (definition.getDepositName().equals("nether/lava_deposit.json")) {
                TKCYALog.logger.info("nether/lava_deposit.json DEPOSIT DETECTED!");

                WorldGenRegistry.INSTANCE.removeVeinDefinitions(definition);
            }

             */
        }
    }

    /**
     * Extracts files from the TKCYA jar and places them in the specified location
     *
     * @param configPath The path of the config root for the Gregtech mod
     * @param targetPath The path of the target location where the files will be initialized
     * @throws IOException
     */
    private static void extractJarVeinDefinitions(@Nonnull Path configPath, Path targetPath, Map<Path, List<String>> oreVeinsToAdd) throws IOException {
        // The path of the worldgen folder in the config folder
        Path worldgenRootPath = configPath.resolve("worldgen");
        // The path of the worldgen folder in the config folder
        Path tkcyaWorldgenRootPath = worldgenRootPath.resolve("tkcya");
        // The path of the physical vein folder in the config folder
        Path oreVeinRootPath = tkcyaWorldgenRootPath.resolve("vein");
        // The path of the bedrock fluid vein folder in the config folder
        Path bedrockFluidVeinRootPath = tkcyaWorldgenRootPath.resolve("fluid");
        FileSystem zipFileSystem = null;
        try {
            URI sampleUri = TKCYAWorldGenRegistry.class.getResource("/assets/tkcya/.tkcyaassetsroot").toURI(); //"/assets/tkcya/.tkcyaassetsroot"
            // The Path for representing the vein folder in the vein folder in the assets folder in the Gregtech resources folder in the jar
            Path oreVeinJarRootPath;
            // The Path for representing the fluid folder in the vein folder in the assets folder in the Gregtech resources folder in the jar
            Path bedrockFluidJarRootPath;
            if (sampleUri.getScheme().equals("jar") || sampleUri.getScheme().equals("zip")) {
                zipFileSystem = FileSystems.newFileSystem(sampleUri, Collections.emptyMap());
                oreVeinJarRootPath = zipFileSystem.getPath("/assets/tkcya/worldgen/vein");
                bedrockFluidJarRootPath = zipFileSystem.getPath("/assets/tkcya/worldgen/fluid");
            } else if (sampleUri.getScheme().equals("file")) {
                oreVeinJarRootPath = Paths.get(TKCYAWorldGenRegistry.class.getResource("/assets/tkcya/worldgen/vein").toURI());
                bedrockFluidJarRootPath = Paths.get(TKCYAWorldGenRegistry.class.getResource("/assets/tkcya/worldgen/fluid").toURI());
            } else {
                throw new IllegalStateException("Unable to locate absolute path to TKCYA worldgen root directory: " + sampleUri);
            }

            // Attempts to extract the worldgen definition jsons
            if (targetPath.compareTo(oreVeinRootPath) == 0) {
                TKCYALog.logger.info("Attempting retrieval of standard TKCYA worldgen definitions from {} to {}",
                        oreVeinJarRootPath, oreVeinRootPath);
                // Find all the default worldgen files in the assets folder
                List<Path> jarFiles = Files.walk(oreVeinJarRootPath)
                        .filter(Files::isRegularFile)
                        .collect(Collectors.toList());

                // Retrieves the TKCYA worldgen files
                for (Path jarFile : jarFiles) {
                    oreVeinsToAdd.computeIfAbsent(jarFile.getParent(), k -> new ArrayList<>());
                    String name = (getActualVeinName(jarFile));
                    oreVeinsToAdd.get(jarFile.getParent()).add(name);
                    Path path = worldgenRootPath.resolve(name);
                    Files.createDirectories(path.getParent());
                    Files.copy(jarFile, path, StandardCopyOption.REPLACE_EXISTING);
                }
                TKCYALog.logger.info("Retrieved {} builtin TKCYA worldgen vein definitions", jarFiles.size());
            } else if (targetPath.compareTo(bedrockFluidVeinRootPath) == 0) {
                TKCYALog.logger.info("Attempting retrieval of standard TKCYA worldgen definitions from {} to {}",
                        bedrockFluidJarRootPath, bedrockFluidVeinRootPath);
                // Find all the default worldgen files in the assets folder
                List<Path> jarFiles = Files.walk(bedrockFluidJarRootPath)
                        .filter(Files::isRegularFile)
                        .collect(Collectors.toList());

                // Replaces or creates the default worldgen files
                for (Path jarFile : jarFiles) {
                    oreVeinsToAdd.computeIfAbsent(jarFile.getParent(), k -> new ArrayList<>());
                    String name = (getActualVeinName(jarFile));
                    oreVeinsToAdd.get(jarFile.getParent()).add(name);
                    Path path = worldgenRootPath.resolve(name);
                    Files.createDirectories(path.getParent());
                    Files.copy(jarFile, path, StandardCopyOption.REPLACE_EXISTING);
                }
                TKCYALog.logger.info("Retrieved {} builtin worldgen bedrock fluid definitions into fluid folder", jarFiles.size());
            }

        } catch (URISyntaxException impossible) {
            //this is impossible, since getResource always returns valid URI
            throw new RuntimeException(impossible);
        } finally {
            if (zipFileSystem != null) {
                //close zip file system to avoid issues
                IOUtils.closeQuietly(zipFileSystem);
            }
        }
    }

    @Nonnull
    private static String getActualVeinName(@Nonnull Path path) {

        //String separator = FileSystems.getDefault().getSeparator(); !!!! Works on Linux, on Windows it returns <\> !!!!

        String separator = System.getProperty("os.name").contains("Windows") ? "/" : FileSystems.getDefault().getSeparator();
        String[] split = path.toString().split("/");
        int count = split.length - 1;

        String veinName = "tkcya";
        for (int i = count - 2; i >= 0; i--) {
            veinName += separator + split[count - i];
        }
        return veinName;

    }
}
