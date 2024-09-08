package com.lemonsoft.lemonlauncher.client;

import com.lemonsoft.lemonlauncher.data.MCAccount;
import com.lemonsoft.lemonlauncher.data.MCInfo;
import com.lemonsoft.lemonlauncher.form.RunScreen;
import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.download.DownloadList;
import fr.flowarg.flowupdater.download.IProgressCallback;
import fr.flowarg.flowupdater.download.Step;
import fr.flowarg.flowupdater.versions.VanillaVersion;
import fr.flowarg.flowupdater.versions.fabric.FabricVersion;
import fr.flowarg.flowupdater.versions.fabric.FabricVersionBuilder;
import fr.flowarg.flowupdater.versions.forge.ForgeVersion;
import fr.flowarg.flowupdater.versions.forge.ForgeVersionBuilder;
import fr.flowarg.openlauncherlib.NoFramework;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.minecraft.GameFolder;
import java.nio.file.Path;
import java.text.DecimalFormat;

import static com.lemonsoft.lemonlauncher.util.FileUtil.getInstance;

/**
 *
 * @author lone64dev
 * 
 */
public class MCClient {
    
    private final MCInfo client;
    private final MCAccount account;
    
    public MCClient(MCInfo client, MCAccount account) {
        this.client = client;
        this.account = account;
    }
    
    public void play() {
        new Thread(this::update).start();
    }

    private void update() {
        IProgressCallback callback = new IProgressCallback() {
            private final DecimalFormat decimalFormat = new DecimalFormat("#.#");
            private String stepTxt = "";
            private String percentTxt = "0.0%";

            @Override
            public void step(Step step) {
                stepTxt = StepInformation.valueOf(step.name()).getDetails();
                addConsole(String.format("%s (%s)", stepTxt, percentTxt));
            }

            @Override
            public void update(DownloadList.DownloadInfo info) {
                percentTxt = decimalFormat.format(info.getDownloadedBytes() * 100.d / info.getTotalToDownloadBytes()) + "%";
                addConsole(String.format("%s (%s)", stepTxt, percentTxt));
            }

            @Override
            public void onFileDownloaded(Path path) {
                String p = path.toString();
                addConsole("..." + p.replace(getInstance("instances/" + client.getName()).getAbsolutePath(), ""));
            }
        };

        try {
            final VanillaVersion vanilla = new VanillaVersion.VanillaVersionBuilder().withName(this.client.getMcVersion()).build();
            if (this.client.getModLoader() == NoFramework.ModLoader.FABRIC) {
                final FabricVersion fabric = new FabricVersionBuilder().withFabricVersion(this.client.getModVersion()).withMods(this.client.getMods()).build();
                final FlowUpdater updater = new FlowUpdater.FlowUpdaterBuilder().withVanillaVersion(vanilla).withModLoaderVersion(fabric).withProgressCallback(callback).build();
                updater.update(getInstance("instances/" + this.client.getName()).toPath());
            } else if (this.client.getModLoader() == NoFramework.ModLoader.FORGE) {
                final ForgeVersion forge = new ForgeVersionBuilder().withForgeVersion(this.client.getModVersion()).withMods(this.client.getMods()).build();
                final FlowUpdater updater = new FlowUpdater.FlowUpdaterBuilder().withVanillaVersion(vanilla).withModLoaderVersion(forge).withProgressCallback(callback).build();
                updater.update(getInstance("instances/" + this.client.getName()).toPath());
            } else if (this.client.getModLoader() == NoFramework.ModLoader.VANILLA) {
                final FlowUpdater updater = new FlowUpdater.FlowUpdaterBuilder().withVanillaVersion(vanilla).withProgressCallback(callback).build();
                updater.update(getInstance("instances/" + this.client.getName()).toPath());
            }
            this.startGame();
        } catch (Exception e) {
            System.out.println("[Lone64] " + e.getMessage());
        }
    }

    private void startGame() {
        try {
            final AuthInfos infos = new AuthInfos(this.account.getUsername(), this.account.getProfile().getMcToken().getAccessToken(), this.account.getProfile().getId().toString());
            final NoFramework noFramework = new NoFramework(getInstance("instances/" + this.client.getName()).toPath(), infos, GameFolder.FLOW_UPDATER);
            noFramework.launch(this.client.getMcVersion(), this.client.getModVersion(), this.client.getModLoader());
            System.exit(0);
        } catch (Exception e) {
            System.out.println("[Lone64] " + e.getMessage());
        }
    }

    private void addConsole(String message) {
        RunScreen.getWindow().getDebug().setText(message);
    }

    public enum StepInformation {
        FORGE("Installing the forge..."),
        FABRIC("Installing the fabric..."),
        READ("Reading the json file..."),
        DL_LIBS("Downloading the libraries..."),
        DL_ASSETS("Downloading the assets..."),
        EXTRACT_NATIVES("Extracting the natives..."),
        MODS("Downloading the mods..."),
        EXTERNAL_FILES("Downloading the external files..."),
        POST_EXECUTIONS("Post-installation execution..."),
        MOD_LOADER("Installing the mod loader..."),
        INTEGRATION("integration the mods..."),
        END("Finish!");

        final String details;

        StepInformation(String details) {
            this.details = details;
        }

        public String getDetails() {
            return details;
        }
    }
    
}