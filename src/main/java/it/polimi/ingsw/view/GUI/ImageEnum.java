package it.polimi.ingsw.view.GUI;

public enum ImageEnum {
    APOLLO_PlAYER("GraphicSrc/Plyayer/Apollo.png","APOLLO_PLAYER"),
    ARTEMIS_PLAYER("GraphicSrc/Plyayer/Artemis.png","ARTEMIS_PLAYER"),
    ATHENA_PLAYER("GraphicSrc/Plyayer/Athena.png","ATHENA_PLAYER"),
    ATLAS_PlAYER("GraphicSrc/Plyayer/Atlas.png","ATLAS_PLAYER"),
    DEMETER_PlAYER("GraphicSrc/Plyayer/Demeter.png","DEMETER_PLAYER"),
    HEPHAESTUS_PlAYER("GraphicSrc/Plyayer/Hephaestus.png","HEPHAESTUS_PLAYER"),
    HERA_PlAYER("GraphicSrc/Plyayer/Hera.png","HERA_PLAYER"),
    MEDUSA_PlAYER("GraphicSrc/Plyayer/Medusa.png","MEDUSA_PLAYER"),
    MINOTAUR_PlAYER("GraphicSrc/Plyayer/Minotaur.png","MINOTAUR_PLAYER"),
    PAN_PlAYER("GraphicSrc/Plyayer/Pan.png","PAN_PLAYER"),
    PERSEPHONE_PlAYER("GraphicSrc/Plyayer/Persephone.png","PERSEPHONE_PLAYER"),
    POSEIDON_PlAYER("GraphicSrc/Plyayer/Poseidon.png","POSEIDON_PLAYER"),
    PROMETHEUS_PlAYER("GraphicSrc/Plyayer/Prometheus.png","PROMETHEUS_PLAYER"),
    ZEUS_PlAYER("GraphicSrc/Plyayer/Zeus.png","ZEUS_PLAYER"),
    APOLLO_DEF("GraphicSrc/Gods/Apollo.jpg","APOLLO_DEF"),
    ARTEMIS_DEF("GraphicSrc/Gods/Artemis.jpg","ARTEMIS_DEF"),
    ATHENA_DEF("GraphicSrc/Gods/Athena.jpg","ATHENA_DEF"),
    ATLAS_DEF("GraphicSrc/Gods/Atlas.jpg","ATLAS_DEF"),
    DEMETER_DEF("GraphicSrc/Gods/Demeter.jpg","DEMETER_DEF"),
    HEPHAESTUS_DEF("GraphicSrc/Gods/Hephaestus.jpg","HEPHAESTUS_DEF"),
    HERA_DEF("GraphicSrc/Gods/Hera.jpg","HERA_DEF"),
    MEDUSA_DEF("GraphicSrc/Gods/Medusa.jpg","MEDUSA_DEF"),
    MINOTAUR_DEF("GraphicSrc/Gods/Minotaur.jpg","MINOTAUR_DEF"),
    PAN_DEF("GraphicSrc/Gods/Pan.jpg","PAN_DEF"),
    PERSEPHONE_DEF("GraphicSrc/Gods/Persephone.jpg","PERSEPHONE_DEF"),
    POSEIDON_DEF("GraphicSrc/Gods/Poseidon.jpg","POSEIDON_DEF"),
    PROMETHEUS_DEF("GraphicSrc/Gods/Prometheus.jpg","PROMETHEUS_DEF"),
    ZEUS_DEF("GraphicSrc/Gods/Zeus.jpg","ZEUS_DEF"),
    PODIUM("GraphicSrc/Plyayer/podium.png","PODIUM"),
    PODIUM_GOLD("GraphicSrc/Plyayer/podium_gold.png","PODIUM_GOLD"),
    CLOUD("GraphicSrc/clouds.png","CLOUD"),
    LEVEL1("GraphicSrc/Buildings/level1.png","LEVEL1"),
    LEVEL2("GraphicSrc/Buildings/level2.png","LEVEL2"),
    LEVEL3("GraphicSrc/Buildings/level3.png","LEVEL3"),
    DOME("GraphicSrc/Buildings/level1.png","DOME"),
    BLUE("GraphicSrc/Buildings/BlueWorker.png","BLUE"),
    BROWN("GraphicSrc/Buildings/BrownWorker.png","BROWN"),
    WHITE("GraphicSrc/Buildings/WhiteWorker.png","WHITE"),
    BUILD("GraphicSrc/ExtraAssets/BUILD.gif","BUILD"),
    MOVE("GraphicSrc/ExtraAssets/MOVE.gif","MOVE"),
    BUILD_DOME("GraphicSrc/ExtraAssets/BuildDome.gif","BUILD_DOME"),
    END("GraphicSrc/ExtraAssets/EndTurn.gif","END_TURN"),
    WIN("GraphicSrc/WIN.gif","WIN"),
    LOSE("GraphicSrc/LOSE.gif","LOSE")
    ;


    private String url;
    private String name;

    private ImageEnum(String url, String name) {
        this.url=url;
        this.name=name;
    }

    public static String getUrl(String name){
        for(ImageEnum i:ImageEnum.values()){
            if(i.name.equals(name)){
                return i.url;
            }
        }
        return null;
    }
}
