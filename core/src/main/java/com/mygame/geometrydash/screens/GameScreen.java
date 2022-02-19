package com.mygame.geometrydash.screens;
import static com.mygame.geometrydash.extra.Utils.WIDTH_SCREEN;
import static com.mygame.geometrydash.extra.Utils.WORLD_HEIGHT;
import static com.mygame.geometrydash.extra.Utils.WORLD_WIDTH;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygame.geometrydash.MainGame;
import com.mygame.geometrydash.actors.Obstaculo;
import com.mygame.geometrydash.actors.Obstaculo2;
import com.mygame.geometrydash.actors.Obstaculo3;
import com.mygame.geometrydash.actors.ObstaculoBloque;
import com.mygame.geometrydash.actors.Player;


public class GameScreen extends BaseScreen {
    private Stage stage;
    private Image background,geometrydash_home,button_play;
    private World world;
    private Player player;
    private Obstaculo obs1;
    private Obstaculo2 obs2;
    private Obstaculo3 obs3;
    private ObstaculoBloque bloque;
    private final float ALTURA_OBS =1.65f;

    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera ortCamera;

    public GameScreen(MainGame mainGame) {
        super(mainGame);
        this.world = new World(new Vector2(0, -11), true);
        FitViewport fitViewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        this.stage = new Stage(fitViewport);

        this.ortCamera = (OrthographicCamera) this.stage.getCamera();
        this.debugRenderer = new Box2DDebugRenderer();

    }

    @Override
    public void show() {
       // addFirstScreen();
        addBackground();
        addFloor();
        addPlayer();
        addObs1();
        addObs2();
        addObs3();
        addBloque();

    }

    private void addFirstScreen() {
        this.geometrydash_home = new Image(mainGame.assetManagment.getPantallaInicio());
        this.geometrydash_home.setPosition(WORLD_WIDTH/2,3.5f);
        this.stage.addActor(this.geometrydash_home);

        this.button_play = new Image(mainGame.assetManagment.getBotonPlay());
        this.button_play.setPosition(WORLD_WIDTH/2-this.button_play.getWidth()/2,3f);
        this.stage.addActor(this.button_play);


    }

    private void addFloor() {
        BodyDef bd = new BodyDef();

        bd.position.x = 0;
        bd.position.y = 1.4f;
        bd.type = BodyDef.BodyType.StaticBody;

        Body oBody = world.createBody(bd);

        EdgeShape shape = new EdgeShape();
        shape.set(0,0,WORLD_WIDTH,0);

        FixtureDef fixture = new FixtureDef();
        fixture.shape = shape;

        oBody.createFixture(fixture);

        shape.dispose();

    }
    private void addBackground() {
        this.background = new Image(mainGame.assetManagment.getBackground());
        this.background.setPosition(0, 0);
        this.background.setSize(WORLD_WIDTH, WORLD_HEIGHT);
        this.stage.addActor(this.background);
    }


    private void addPlayer() {
        TextureRegion player = mainGame.assetManagment.getPlayer();
        this.player= new Player(this.world,player,new Vector2(1,2));
        this.stage.addActor(this.player);
    }

    public void addObs1(){
        TextureRegion obs1 = mainGame.assetManagment.getObs1();
        this.obs1 = new Obstaculo(this.world,obs1,new Vector2(4f,ALTURA_OBS));
        this.stage.addActor(this.obs1);
    }

    public void addObs2(){
        TextureRegion obs2 = mainGame.assetManagment.getObs2();
        this.obs2  = new Obstaculo2(this.world,obs2,new Vector2(this.obs1.getX()+5f,ALTURA_OBS+.25f));
        this.stage.addActor(this.obs2);
    }

    public void addObs3(){
        TextureRegion obs3 = mainGame.assetManagment.getObs3();
        this.obs3 = new Obstaculo3(this.world,obs3,new Vector2(this.obs2.getX()+6f,ALTURA_OBS+.5f));
        this.stage.addActor(this.obs3);
    }
    public void addBloque(){
        TextureRegion bloque = mainGame.assetManagment.getBloque();
        this.bloque = new ObstaculoBloque(this.world,bloque,new Vector2(this.obs3.getX()+10f,ALTURA_OBS+.05f));
        this.stage.addActor(this.bloque);
    }


    @Override
    public void render(float delta) {
        this.stage.act();
        this.world.step(delta, 6, 2);
        this.stage.draw();
        this.ortCamera.update();
        this.debugRenderer.render(this.world, this.ortCamera.combined);
    }

    @Override
    public void hide() {
        this.player.detach();
        this.player.remove();



    }

    @Override
    public void dispose() {
        this.stage.dispose();
        this.world.dispose();
    }

    @Override
    public void pause() {
    }
}
