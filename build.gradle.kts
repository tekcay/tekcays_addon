import net.minecraftforge.gradle.user.UserBaseExtension
import java.util.*

buildscript {
    repositories {
        mavenCentral()
        maven {
            name = "Jitpack"
            setUrl("https://jitpack.io")
        }
        maven {
            name = "Forge"
            setUrl("https://maven.minecraftforge.net")
        }
    }
    dependencies {
        classpath("com.github.GregTechCE:ForgeGradle:FG_2.3-SNAPSHOT")
    }
}

apply {
    plugin("net.minecraftforge.gradle.forge")
}

val Project.minecraft: UserBaseExtension
    get() = extensions.getByName<UserBaseExtension>("minecraft")

val config: Properties = file("build.properties").inputStream().let {
    val prop = Properties()
    prop.load(it)
    return@let prop
}

val modVersion = config["tkcya.version"] as String
val mcVersion = config["mc.version"] as String
val forgeVersion = "$mcVersion-${config["forge.version"]}"
val shortVersion = mcVersion.substring(0, mcVersion.lastIndexOf("."))
val strippedVersion = shortVersion.replace(".", "") + "0";

version = "$mcVersion-$modVersion"
group = "gregicality"

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

configure<BasePluginConvention> {
    archivesBaseName = "TekCaysAddon"
}

configure<UserBaseExtension> {
    version = forgeVersion
    mappings = config["mcp.version"] as String
    runDir = "run"
    replace("@VERSION@", modVersion)
    replaceIn("TekCaysAddon.java")
}

repositories {
    maven {
        name = "JEI"
        setUrl("http://dvs1.progwml6.com/files/maven/")
    }
    maven {
        name = "CraftTweaker"
        setUrl("https://maven.blamejared.com/")
    }
    maven {
        name = "CurseForge"
        setUrl("https://minecraft.curseforge.com/api/maven")
    }
    maven {
        name = "CCL, CASM"
        setUrl("http://chickenbones.net/maven/")
    }
    maven {
        name = "CTM"
        setUrl("https://maven.tterrag.com/")
    }
}

dependencies {

    // These 7 will always be in game
    //"deobfCompile"("gregtechce:gregtech:$mcVersion:${config["gregtech.version"]}")
    "deobfCompile"("codechicken-lib-1-8:CodeChickenLib-$mcVersion:${config["ccl.version"]}:universal")
    "deobfCompile"("codechicken:ChickenASM:$shortVersion-${config["chickenasm.version"]}")
    "deobfCompile"("mezz.jei:jei_$mcVersion:${config["jei.version"]}")
    "deobfCompile"("mcjty.theoneprobe:TheOneProbe-$shortVersion:$shortVersion-${config["top.version"]}")
    "deobfCompile"("CraftTweaker2:CraftTweaker2-MC$strippedVersion-Main:${config["crafttweaker.version"]}")
    "deobfCompile"("team.chisel.ctm:CTM:MC$mcVersion-${config["ctm.version"]}")

    "provided"(files("libs/gregtech-1.12.2-${config["gregtech.version"]}.jar"))

    // JUnit testing used for GitHub Actions
    "testImplementation"("junit:junit:${config["junit.version"]}")
}

val processResources: ProcessResources by tasks
val sourceSets: SourceSetContainer = the<JavaPluginConvention>().sourceSets

processResources.apply {
    inputs.property("version", modVersion)
    inputs.property("mcversion", forgeVersion)

    from(sourceSets["main"].resources.srcDirs) {
        include("mcmod.info")
        expand(mapOf("version" to modVersion, "mcversion" to forgeVersion))
    }

    from(sourceSets["main"].resources.srcDirs) {
        exclude("mcmod.info")
    }

    // Access Transformer jar manifest info
    rename("(.+_at.cfg)", "META-INF/$1")
}
