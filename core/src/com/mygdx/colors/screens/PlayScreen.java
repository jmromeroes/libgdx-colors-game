package com.mygdx.colors.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.colors.ColorsGame;
import com.mygdx.colors.background.Background;
import com.mygdx.colors.elements.AccelerationPit;
import com.mygdx.colors.elements.BombTile;
import com.mygdx.colors.elements.DirectionTile;
import com.mygdx.colors.elements.FlappyTile;
import com.mygdx.colors.elements.GravitySwitch;
import com.mygdx.colors.elements.Lock;
import com.mygdx.colors.elements.RegularTile;
import com.mygdx.colors.elements.Spike;
import com.mygdx.colors.elements.Station;
import com.mygdx.colors.elements.Teletransporter;
import com.mygdx.colors.elements.Torch;
import com.mygdx.colors.entities.AngryEnemy;
import com.mygdx.colors.entities.ColorMan;
import com.mygdx.colors.entities.CrazyEnemy;
import com.mygdx.colors.entities.GameEntity;
import com.mygdx.colors.entities.HappyEnemy;
import com.mygdx.colors.entities.HillEnemy;
import com.mygdx.colors.entities.SadEnemy;
import com.mygdx.colors.hud.HUD;
import com.mygdx.colors.hud.PauseBoard;
import com.mygdx.colors.hud.ScoreBoard;
import com.mygdx.colors.hud.TiledButton;
import com.mygdx.colors.input.InputHandler;
import com.mygdx.colors.items.Key;
import com.mygdx.colors.items.Star;
import com.mygdx.colors.particles.Explosion;
import com.mygdx.colors.particles.Particle;
import com.mygdx.colors.utils.ContactListener;
import com.mygdx.colors.utils.Content;
import com.mygdx.colors.utils.GeneralInformation;

public class PlayScreen extends GameScreen{

	//Tile final names
	public static final String EXPLODABLE_TILE = "explodableTile";
	public static final String DIRECTION_TILE = "directionTile";
	public static final String STAR = "star";
	public static final String TORCH = "torch";
	public static final String KEY = "key";
	public static final String LOCK = "lock";
	public static final String SPIKES = "spikes";
	public static final String GRAVITATIONAL_SWITCH = "gravity_switch";
	public static final String STATION = "station";
	public static final String ACCELERATION_PIT = "accelerationPit";
	public static final String SQUARED_ENEMY = "squaredEnemy";
	public static final String HILL_ENEMY = "hillEnemy";
	public static final String BOMB = "bomb";
	public static final String FLAPPY_TILE = "flappy_tile";
	public static final String TELETRANSPORTER = "teletransporter";
	
	//Pause
	private boolean paused;
	
	//Tiled management
	private TiledMap tiledMap;
	private int tiledMapWidth;
	private int tiledMapHeight;
	private float tileSize;
	private int level;
	
	//Initial World Position
	private float initialMapX;
	
	//Tiles and elements' arrays
	private GameEntity[][] gameEntitiesArray; 
	
	//Input Management
	private InputHandler inputHandler;
	private InputMultiplexer inputMultiplexer;
	private GestureDetector gestureDetector;
	
	//HUD
	private HUD hud;
	private ScoreBoard scoreBoard;
	private PauseBoard pauseBoard;
	private TiledButton pauseButton;
	
	//Character
	private ColorMan colorMan;
	private boolean levelFinished;
	private float timeToCreateBoard;
	private float timeAfterLevelFinished;
	
	//On Tiles
	private GameEntity onTile1, onTile2, onTile3, onTile4, onTile5, onTile6, onTile7, onTile8;
	
	//Explosions
	private ArrayList<Explosion> explosions;
	private float vibrationReferenceX, vibrationReferenceY;
	
	//Enemies
	private ArrayList<GameEntity> enemies;
	
	//Sounds
	private Sound levelFinishedSound;
	
	//Music
	private Music mainTheme;
	
	//Background
	private Background background;
	private Background background2;
	
	public PlayScreen(ColorsGame colorsGame){
		super(colorsGame);
		this.levelFinished = false;
		this.timeToCreateBoard = 0.6f;
		this.paused = false;
		
		levelFinishedSound = Content.getInstance().getSound("powerUp");
		
		colorMan = new ColorMan(this);
		initialMapX = GeneralInformation.INITIAL_MAP_X;
		
		//Main arrays
		explosions = new ArrayList<Explosion>();
		
		//HUD
		hud = new HUD(this);
		
	}

	@Override
	public void render(float delta){
			update(delta);
			
			background.render(getSpriteBatch());
			background2.render(getSpriteBatch());
			
			//Values to optimize updating and rendering processes
			float cameraY = getCamera().getPosition().y-getGame().getVHeight()/2;
			float cameraX = getCamera().getPosition().x-getGame().getVWidth()/2;
			
			int min_y = (int)Math.max(0, cameraY/tileSize)-3;
			int max_y = (int)Math.min((cameraY+getGame().getVHeight())/tileSize,
					                  	tiledMapHeight)+1;
			int min_x = (int)Math.max(0, cameraX/tileSize)-3;
			int max_x = (int)Math.min((cameraX+getGame().getVWidth()-initialMapX*2)/tileSize,
					                  	tiledMapWidth)+1;
			
		    for(int y = min_y; y < max_y; y++){
				for(int x = min_x; x < max_x; x++){
					try{
						if(gameEntitiesArray[y][x] != null)
							gameEntitiesArray[y][x].render(getSpriteBatch());
							
					}catch(ArrayIndexOutOfBoundsException exception){}
				}
			}
		    
		    if(!enemies.isEmpty()){
				for(GameEntity enemy: enemies){
					int min_en_x = (int)((enemy.getPosition().x-GeneralInformation.INITIAL_MAP_X)/getTileSize())-1;
					int max_en_x = (int)((enemy.getPosition().x+enemy.getWidth()-GeneralInformation.INITIAL_MAP_X)/getTileSize())+1;
					
					int min_en_y = (int)((enemy.getPosition().y)/getTileSize())-1;
					int max_en_y = (int)((enemy.getPosition().y+enemy.getHeight())/getTileSize())+1;
					
					if(min_en_x>min_x-5 && max_en_x<max_x+5){
						if(min_en_y>min_y-5 && max_en_y<max_y+5){
							enemy.render(getSpriteBatch());
						}
					}
				}
			}

		    
		    Content.getInstance().getFont(GeneralInformation.MAIN_FONT).draw(getSpriteBatch(), 
		    		""+Gdx.graphics.getFramesPerSecond(), 
		    		getCamera().getPosition().x-getGame().getVWidth()/2
		    		+GeneralInformation.INITIAL_MAP_X, 
		    		getCamera().getPosition().y);
			for(Explosion explosion: explosions){
				explosion.render(getSpriteBatch());
			}
			
			colorMan.render(getSpriteBatch());
					
			hud.render(getSpriteBatch());
			
			if(pauseButton != null){
				pauseButton.render(getSpriteBatch());
			}
			
			if(pauseBoard != null){
				pauseBoard.render(getSpriteBatch());
			}
			
			if(scoreBoard != null){
				scoreBoard.render(getSpriteBatch());
			}
	}

	@Override
	public void update(float delta){
		
		if(!ColorsGame.soundEnabled && mainTheme.isPlaying()){
			mainTheme.pause();
		}else if(!mainTheme.isPlaying() && ColorsGame.soundEnabled){
			mainTheme.play();
		}
		
		if(!paused){
			hud.update(delta);
			//First we calculate gravity to update dx and dy values from character
			colorMan.calculateGravity();
			
			//Values to optimize updating and rendering processes
			float cameraY = getCamera().getPosition().y-getGame().getVHeight()/2;
			int min_y = (int)Math.max(0, cameraY/tileSize);
			int max_y = (int)Math.min((cameraY+getGame().getVHeight())/tileSize,
					                  	tiledMapHeight)+1;
			
			float cameraX = getCamera().getPosition().x-getGame().getVWidth()/2;
			int min_x = (int)Math.max(0, cameraX/tileSize);
			int max_x = (int)Math.min((cameraX+getGame().getVWidth()-initialMapX*2)/tileSize,
                  	tiledMapWidth)+1;
			
			//Collisions between colorman and tiles
			for(int y = min_y; y < max_y; y++){
				for(int x = min_x; x < max_x; x++){
					try{
					if(gameEntitiesArray[y][x] != null)
						ContactListener.checkCollisionBetweenEntities(gameEntitiesArray[y][x], colorMan);
					}catch(ArrayIndexOutOfBoundsException e){}
				}
			}
			
			for(int y = min_y; y < max_y; y++){
				for(int x = min_x; x < max_x; x++){
					try{
						if(gameEntitiesArray[y][x] != null){
							if(!gameEntitiesArray[y][x].isHidden()){
								gameEntitiesArray[y][x].update(delta);
							}
						}
					}catch(Exception e){}
				}
			}
			
			if(!enemies.isEmpty()){
				for(GameEntity enemy: enemies){
					int min_en_x = (int)((enemy.getPosition().x-GeneralInformation.INITIAL_MAP_X)/getTileSize())-1;
					int max_en_x = (int)((enemy.getPosition().x+enemy.getWidth()-GeneralInformation.INITIAL_MAP_X)/getTileSize())+1;
					
					int min_en_y = (int)((enemy.getPosition().y)/getTileSize())-1;
					int max_en_y = (int)((enemy.getPosition().y+enemy.getHeight())/getTileSize())+1;
					
					if(min_en_x>min_x-6 && max_en_x<max_x+6){
						if(min_en_y>min_y-6 && max_en_y<max_y+6){
						enemy.update(delta);
						ContactListener.checkCollisionBetweenEntities(enemy, colorMan);
						for(int y = min_en_y; y < max_en_y; y++){
							for(int x = min_en_x; x < max_en_x; x++){
								try{
									if(gameEntitiesArray[y][x] != null){
										ContactListener.checkCollisionBetweenEntities(enemy, gameEntitiesArray[y][x]);
									}
								}catch(ArrayIndexOutOfBoundsException exception){}
							}
						}
						}
					}
				}
			}
			
			//Update and check explosion extinction
			for(int i = 0; i < explosions.size(); i++){
				Explosion explosion = explosions.get(i);
				explosion.update(delta);
				if(explosion.isExtinct()){
					explosions.remove(i);
					i--;
				}
				
				if(isShaking())
					getCamera().setPosition(vibrationReferenceX+(float)Math.cos(Math.random()*Math.PI*4)*7, 
										vibrationReferenceY+(float)Math.sin(Math.random()*Math.PI*4)*7);
			}
			
			//Check tiles activation
			//THIS FUNCTION MUST BE CALLED AFTER UPDATING THE TILES
			checkTilesActivation();
			
			//Update player respecting to the collisions
			colorMan.update(delta);
			
			if(levelFinished){
				timeAfterLevelFinished += delta;
				if(timeAfterLevelFinished > timeToCreateBoard)
					if(scoreBoard == null){
						scoreBoard = new ScoreBoard(this, level, colorMan.getStars());
						if(ColorsGame.soundEnabled)
							levelFinishedSound.play(0.2f);
					}else{
						scoreBoard.update(delta);
					}
			}
			
			//Update general game state depending on colorMan life
			if(colorMan.isDead()){
				setShaking(true);
				Gdx.input.vibrate(1000);
				restart();
			}else{
				vibrationReferenceX = getCamera().getPosition().x;
				vibrationReferenceY = getCamera().getPosition().y;
			}
			
			if(pauseButton != null){
				pauseButton.update(delta);
			}
			
			background.update(1.7f+getCamera().getDx(), getCamera().getDy(), true);
			background2.update(0.8f+getCamera().getDx(), getCamera().getDy(), true);
		}else{
			pauseBoard.update(delta);
			if(pauseButton.isActivated()){
				pauseButton = new TiledButton(this, TiledButton.PAUSE, getCamera().getPosition().x,
						 getGame().getVHeight()-100);
			}
			pauseButton.update(delta);
		}
	}

	public TiledMap getTiledMap() {
		return tiledMap;
	}

	public void setTiledMap(TiledMap tiledMap) {
		this.tiledMap = tiledMap;
	}

	public int getTiledMapWidth() {
		return tiledMapWidth;
	}

	public void setTiledMapWidth(int tiledMapWidth) {
		this.tiledMapWidth = tiledMapWidth;
	}

	public int getTiledMapHeight() {
		return tiledMapHeight;
	}

	public void setTiledMapHeight(int tiledMapHeight) {
		this.tiledMapHeight = tiledMapHeight;
	}

	public float getTileSize() {
		return tileSize;
	}

	public void setTileSize(float tileSize) {
		this.tileSize = tileSize;
	}
	
	
	public void createElements(){
		tiledMap = new TmxMapLoader().load("assets/Levels/level"+level+".tmx");
		tileSize = (Integer)tiledMap.getProperties().get("tilewidth");
		tiledMapWidth = (Integer)tiledMap.getProperties().get("width");
		tiledMapHeight = (Integer)tiledMap.getProperties().get("height");
		gameEntitiesArray = new GameEntity[tiledMapHeight][tiledMapWidth]; 
		
		TiledMapTileLayer layer;
			
		layer = (TiledMapTileLayer)tiledMap.getLayers().get("tilesLayer");

		createLayer(layer);
	}
	
	public void createLayer(TiledMapTileLayer layer){
		
		for(int row = 0; row < layer.getHeight(); row++){
			for(int col = 0; col < layer.getWidth();col++){
				Cell cell  = layer.getCell(col, row);
				if(cell == null) continue;
				if(cell.getTile() == null) continue;
				
				if(cell.getTile().getProperties().get("type").equals(EXPLODABLE_TILE)){
					RegularTile explodableTile= new RegularTile(cell);
					explodableTile.setPosition(initialMapX+col*tileSize, row*tileSize);
					gameEntitiesArray[row][col]=explodableTile;
				}else if(cell.getTile().getProperties().get("type").equals(DIRECTION_TILE)){
					DirectionTile directionTile = new DirectionTile(cell);
					directionTile.setPosition(initialMapX+col*tileSize, row*tileSize);
					gameEntitiesArray[row][col]=directionTile;
				}else if(cell.getTile().getProperties().get("type").equals(STAR)){
					Star star = new Star(this);
					star.setPosition(initialMapX+col*tileSize, row*tileSize);
					gameEntitiesArray[row][col]=star;
				}else if(cell.getTile().getProperties().get("type").equals(TORCH)){
					Torch torch = new Torch(this);
					torch.setPosition(initialMapX+col*tileSize, row*tileSize);
					gameEntitiesArray[row][col]=torch;
				}else if(cell.getTile().getProperties().get("type").equals(KEY)){
					Key key = new Key(this, cell);
					key.setPosition(initialMapX+col*tileSize, row*tileSize);
					gameEntitiesArray[row][col]=key;
				}else if(cell.getTile().getProperties().get("type").equals(LOCK)){
					Lock lock = new Lock(this, cell, col, row);
					lock.setPosition(initialMapX+col*tileSize, row*tileSize);
					gameEntitiesArray[row][col]=lock;
				}else if(cell.getTile().getProperties().get("type").equals(SPIKES)){
					Spike spikes = new Spike();
					spikes.setPosition(initialMapX+col*tileSize, row*tileSize-2);
					gameEntitiesArray[row][col]=spikes;
				}else if(cell.getTile().getProperties().get("type").equals(GRAVITATIONAL_SWITCH)){
					GravitySwitch gravitationalSwitch = new GravitySwitch(cell);
					gravitationalSwitch.setPosition(initialMapX+col*tileSize, row*tileSize);
					gameEntitiesArray[row][col]=gravitationalSwitch;
				}else if(cell.getTile().getProperties().get("type").equals(STATION)){
					Station station = new Station(cell);
					station.setPosition(initialMapX+col*tileSize, row*tileSize);
					gameEntitiesArray[row][col]=station;
				}else if(cell.getTile().getProperties().get("type").equals(ACCELERATION_PIT)){
					AccelerationPit accelerationPit = new AccelerationPit(cell);
					accelerationPit.setPosition(initialMapX+col*tileSize, row*tileSize);
					gameEntitiesArray[row][col]=accelerationPit;
				}else if(cell.getTile().getProperties().get("type").equals(SQUARED_ENEMY)){
					String color = (String)cell.getTile().getProperties().get("color");
					if(color.equals(GeneralInformation.RED)){
						AngryEnemy angryEnemy = new AngryEnemy(this);
						angryEnemy.setPosition(initialMapX+col*tileSize, row*tileSize);
						enemies.add(angryEnemy);
					}else if(color.equals(GeneralInformation.GREEN)){
						HappyEnemy happyEnemy = new HappyEnemy(this);
						happyEnemy.setPosition(initialMapX+col*tileSize, row*tileSize);
						enemies.add(happyEnemy);
					}else if(color.equals(GeneralInformation.BLUE)){
						CrazyEnemy crazyEnemy = new CrazyEnemy(this);
						crazyEnemy.setPosition(initialMapX+col*tileSize, row*tileSize);
						enemies.add(crazyEnemy);
					}else if(color.equals(GeneralInformation.YELLOW)){
						SadEnemy sadEnemy = new SadEnemy(this);
						sadEnemy.setPosition(initialMapX+col*tileSize, row*tileSize);
						enemies.add(sadEnemy);
					}
				}else if(cell.getTile().getProperties().get("type").equals(HILL_ENEMY)){
					HillEnemy hillEnemy = new HillEnemy(this, cell);
					hillEnemy.setPosition(initialMapX+col*tileSize, row*tileSize);
					enemies.add(hillEnemy);
				}else if(cell.getTile().getProperties().get("type").equals(BOMB)){
					BombTile bomb = new BombTile(this,cell);
					bomb.setPosition(initialMapX+col*tileSize, row*tileSize);
					gameEntitiesArray[row][col]=bomb;
				}else if(cell.getTile().getProperties().get("type").equals(FLAPPY_TILE)){
					FlappyTile flappyTile = new FlappyTile(cell);
					flappyTile.setPosition(initialMapX+col*tileSize, row*tileSize);
					gameEntitiesArray[row][col]=flappyTile;
				}else if(cell.getTile().getProperties().get("type").equals(TELETRANSPORTER)){
					Teletransporter teletransporter = new Teletransporter(this,cell, col, row);
					teletransporter.setPosition(initialMapX+col*tileSize, row*tileSize);
					gameEntitiesArray[row][col]=teletransporter;
				}
			}
		}
	}
	
	@Override
	public void touchDown(int screenX, int screenY, int pointer, int button){
		hud.touchDown(screenX, screenY, pointer);
		
		if(!paused)
			pauseButton.touchDown(screenX, screenY, pointer, button);
		
		if(colorMan.isFlappy()){
			colorMan.impulse(0, isSubgravitational()?-10:10);
			if(ColorsGame.soundEnabled)
				colorMan.getFlappySound().play();
		}
		
		if(scoreBoard != null){
			scoreBoard.touchDown(screenX, screenY, pointer, button);
		}
		
		if(pauseBoard != null){
			pauseBoard.touchDown(screenX, screenY, pointer, button);
		}
	}
	
	@Override
	public void touchUp(int screenX, int screenY, int pointer, int button){
		hud.touchUp(screenX, screenY, pointer);
	}
	
	public void activateTiles(String color){
		boolean tileActivated = false;
		if(onTile1 != null && onTile1.getColor() != null && onTile1.getColor().equals(color)){
			onTile1.activate_action(colorMan);
			tileActivated = true;
		}
		
		if(onTile2 != null && onTile2.getColor() != null && onTile2.getColor().equals(color)){
			onTile2.activate_action(colorMan);
			tileActivated = true;
		}
		
		if(onTile3 != null && onTile3.getColor() != null && onTile3.getColor().equals(color)){
			onTile3.activate_action(colorMan);
			tileActivated = true;
		}
		
		if(onTile4 != null && onTile4.getColor() != null && onTile4.getColor().equals(color)){
			onTile4.activate_action(colorMan);
			tileActivated = true;
		}
		
		if(!tileActivated){
			if(onTile5 != null && onTile5.getColor() != null && onTile5.getColor().equals(color)){
				onTile5.activate_action(colorMan);
				tileActivated = true;
			}
		
			if(onTile6 != null && onTile6.getColor() != null && onTile6.getColor().equals(color)){
				onTile6.activate_action(colorMan);
				tileActivated = true;
			}
		
			if(onTile7 != null && onTile7.getColor() != null && onTile7.getColor().equals(color)){
				onTile7.activate_action(colorMan);
				tileActivated = true;
			}
		
			if(onTile8 != null && onTile8.getColor() != null && onTile8.getColor().equals(color)){
				onTile8.activate_action(colorMan);
				tileActivated = true;
			}
		}
		
		
		if(!tileActivated && !colorMan.getCurrentState().getName().equals(ColorMan.FLAPPY)){
			colorMan.lifeDown();
		}
	}
	
	public void checkTilesActivation(){
		int colorManX = Math.round((colorMan.getPosition().x-initialMapX+colorMan.getWidth()/2)/tileSize);
		int colorManY = Math.round((colorMan.getPosition().y+colorMan.getHeight()/2)/tileSize);
		
		try{
			if(colorManX < tiledMapWidth && colorManY < tiledMapHeight 
					&& colorManX >= 0 && colorManY >= 0){
				onTile1 = (gameEntitiesArray[colorManY][colorManX+1]==null && colorManX+2<tiledMapWidth)?
						gameEntitiesArray[colorManY][colorManX+2]:gameEntitiesArray[colorManY][colorManX+1];
				
				onTile2 = (gameEntitiesArray[colorManY][colorManX-1]==null && colorManX-2>=0)?
						gameEntitiesArray[colorManY][colorManX-2]:gameEntitiesArray[colorManY][colorManX-1];
				
				onTile3 = (gameEntitiesArray[colorManY+1][colorManX]==null && colorManY+2<tiledMapHeight)?
						gameEntitiesArray[colorManY+2][colorManX]:gameEntitiesArray[colorManY+1][colorManX];
			
				onTile4 = (gameEntitiesArray[colorManY-1][colorManX]==null && colorManY-2>=0)?
						gameEntitiesArray[colorManY-2][colorManX]:gameEntitiesArray[colorManY-1][colorManX];
				
				if(colorMan.getDy()>0){
					onTile5 = (gameEntitiesArray[colorManY-1][colorManX+1]==null && colorManX+2<tiledMapWidth)?
							gameEntitiesArray[colorManY-1][colorManX+2]:gameEntitiesArray[colorManY-1][colorManX+1];
					onTile6 = (gameEntitiesArray[colorManY-1][colorManX-1]==null && colorManX-2<tiledMapWidth)?
							gameEntitiesArray[colorManY-1][colorManX-2]:gameEntitiesArray[colorManY-1][colorManX+1];
				}else{
					onTile5 = (gameEntitiesArray[colorManY+1][colorManX+1]==null && colorManX+2<tiledMapWidth)?
							gameEntitiesArray[colorManY+1][colorManX+2]:gameEntitiesArray[colorManY-1][colorManX+1];
					onTile6 = (gameEntitiesArray[colorManY+1][colorManX+1]==null && colorManX+2<tiledMapWidth)?
							gameEntitiesArray[colorManY+1][colorManX+2]:gameEntitiesArray[colorManY-1][colorManX+1];
				}
				
				if(colorMan.getDx()>0){
					onTile7 = (gameEntitiesArray[colorManY+1][colorManX-1]==null && colorManY+2<tiledMapHeight)?
							gameEntitiesArray[colorManY+2][colorManX-1]:gameEntitiesArray[colorManY+1][colorManX];
					onTile8 = (gameEntitiesArray[colorManY-1][colorManX-1]==null && colorManY-2>=0)?
							  gameEntitiesArray[colorManY-2][colorManX-1]:gameEntitiesArray[colorManY-1][colorManX];
				}else if(colorMan.getDx()<0){
					onTile7 = (gameEntitiesArray[colorManY+1][colorManX+1]==null && colorManY+2<tiledMapHeight)?
							gameEntitiesArray[colorManY+2][colorManX+1]:gameEntitiesArray[colorManY+1][colorManX];
					onTile8 = (gameEntitiesArray[colorManY-1][colorManX+1]==null && colorManY-2>=0)?
							gameEntitiesArray[colorManY-2][colorManX+1]:gameEntitiesArray[colorManY-1][colorManX];
				}else if(colorMan.getCurrentState().getName().equals(ColorMan.WALK_RIGHT)){
					onTile7 = (gameEntitiesArray[colorManY+1][colorManX-1]==null && colorManY+2<tiledMapHeight)?
							gameEntitiesArray[colorManY+2][colorManX-1]:gameEntitiesArray[colorManY+1][colorManX];
					onTile8 = (gameEntitiesArray[colorManY-1][colorManX-1]==null && colorManY-2>=0)?
							  gameEntitiesArray[colorManY-2][colorManX-1]:gameEntitiesArray[colorManY-1][colorManX];
				}else if(colorMan.getCurrentState().getName().equals(ColorMan.WALK_LEFT)){
					onTile7 = (gameEntitiesArray[colorManY+1][colorManX+1]==null && colorManY+2<tiledMapHeight)?
							gameEntitiesArray[colorManY+2][colorManX+1]:gameEntitiesArray[colorManY+1][colorManX];
					onTile8 = (gameEntitiesArray[colorManY-1][colorManX+1]==null && colorManY-2>=0)?
							gameEntitiesArray[colorManY-2][colorManX+1]:gameEntitiesArray[colorManY-1][colorManX];
				}
			}else{
				colorMan.setDead(true);
			}
		}catch(ArrayIndexOutOfBoundsException exception){
			if(ColorsGame.soundEnabled)
				colorMan.getDieSound().play();
			colorMan.setDead(true);
			addExplosion(colorMan.getPosition().x+colorMan.getWidth()/2,
						 colorMan.getPosition().y+colorMan.getHeight()/2, 
						 Particle.COLORS_MAN_PARTICLE, 10);
		}
		
		if(onTile1 != null && onTile1.isActivableByColor())
			onTile1.setCurrentState(GeneralInformation.ON);
		
		if(onTile2 != null && onTile2.isActivableByColor())
			onTile2.setCurrentState(GeneralInformation.ON);
		
		if(onTile3 != null && onTile3.isActivableByColor())
			onTile3.setCurrentState(GeneralInformation.ON);
		
		if(onTile4 != null && onTile4.isActivableByColor())
			onTile4.setCurrentState(GeneralInformation.ON);
		
		if(onTile5 != null && onTile5.isActivableByColor())
			onTile5.setCurrentState(GeneralInformation.ON);
		
		if(onTile6 != null && onTile6.isActivableByColor())
			onTile6.setCurrentState(GeneralInformation.ON);
		
		if(onTile7 != null && onTile7.isActivableByColor())
			onTile7.setCurrentState(GeneralInformation.ON);
		
		if(onTile8 != null && onTile8.isActivableByColor())
			onTile8.setCurrentState(GeneralInformation.ON);
	}
	
	public void addExplosion(float posX, float posY, String particleTextureName, int numberOfParticles){
		Explosion explosion = new Explosion(posX, posY, particleTextureName, numberOfParticles);
		explosions.add(explosion);
	}
	
	public void restart(){
		colorMan.setPosition(colorMan.getInitialPosition());
		colorMan.restartValues();
		
		if(ColorsGame.soundEnabled)
			mainTheme.play();
		
		paused = false;
		
		if(isSubgravitational()){
			flipSubgravitation();
		}
	
		
		if(explosions.size()==0){
			colorMan.setDead(false);
			colorMan.setDx(colorMan.getSpeed());
			setShaking(false);
			getCamera().update();
			Gdx.input.cancelVibrate();
			background.setX(getCamera().getPosition().x-getGame().getVWidth()/2);
			background.setY(getCamera().getPosition().y-getGame().getVHeight()/2);
			
			for(GameEntity enemy: enemies){
				enemy.restartValues();
			}
		}
		
		for(int y = 0; y < tiledMapHeight; y++){
			for(int x = 0; x < tiledMapWidth; x++){
                if(gameEntitiesArray[y][x] != null){
                	gameEntitiesArray[y][x].setHidden(false);
                	if(gameEntitiesArray[y][x] instanceof Torch){
                		gameEntitiesArray[y][x].setCurrentState(Torch.OFF);
                	}
                	if(gameEntitiesArray[y][x] instanceof Station){
                		gameEntitiesArray[y][x].setCurrentState(Station.PLAY);
                	}
                }
			}
		}
		
	}

	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level){
		//Background
		background = new Background(this, 3, "background");
		background2 = new Background(this, 3f, "background2");
		
		this.levelFinished = false;
		this.paused = false;
		
		
		getCamera().setGameScreen(this);
		/*Set the level you want or just leave this.level = level */
		this.level = level;
		/*--------------------------------------------------------*/
		
		this.scoreBoard = null;
		this.pauseBoard = null;
		this.timeAfterLevelFinished = 0;
		this.gameEntitiesArray = null;
		this.enemies = null;
		this.enemies = new ArrayList<GameEntity>();
		this.colorMan.setAccelerating(false);
		this.colorMan.restartValues();
		
		//Create everything in level
		createElements();
				
		//Map Management
		float tiledTotalHeight = tiledMapHeight*tileSize;
		float tiledTotalWidth = tiledMapWidth*tileSize;
		
		MapProperties prop = tiledMap.getProperties();
		int tileWidth = prop.get("tilewidth", Integer.class);
		int tileHeight = prop.get("tileheight", Integer.class);
		
		int posX, posY;
		
		if((String)prop.get("playerX") != null && (String)prop.get("playerY") != null){
			posX = Integer.parseInt((String)prop.get("playerX"))*tileWidth;
			posY = Integer.parseInt((String)prop.get("playerY"))*tileHeight;
		}else{
			posX = tileWidth+20;
			posY = tileHeight+20;
		}
		
		//Main Character
		if(colorMan.getPosition() != null){
			colorMan.setPosition(null);
		}
		colorMan.setPosition(GeneralInformation.INITIAL_MAP_X+posX, posY);
		colorMan.setCurrentState(ColorMan.WALK_RIGHT);
		colorMan.setDx(colorMan.getSpeed());
		
		//CameraStuff
		getCamera().setBounds(0, tiledTotalWidth+GeneralInformation.INITIAL_MAP_X*2, 0, tiledTotalHeight);
		getCamera().setColorMan(colorMan);
		getCamera().update();
		getCamera().setDx(0);
		getCamera().setDy(0);
		
		pauseButton = new TiledButton(this, TiledButton.PAUSE, getCamera().getPosition().x,
				 getGame().getVHeight()-100);
	
		background.setX(getCamera().getPosition().x-getGame().getVWidth()/2);
		background.setY(getCamera().getPosition().y-getGame().getVHeight()/2);
		
				
	}

	public boolean isLevelFinished() {
		return levelFinished;
	}

	public void setLevelFinished(boolean levelFinished) {
		this.levelFinished = levelFinished;
	}
	
	public GameEntity[][] getGameEntitiesArray() {
		return this.gameEntitiesArray;
	}

	public void setGameEntitiesArray(GameEntity[][] gameEntitiesArray) {
		this.gameEntitiesArray = gameEntitiesArray;
	}
	
	public void keyPressed(){
		if(colorMan.isFlappy()){
			colorMan.impulse(0, isSubgravitational()?-10:10);

			if(ColorsGame.soundEnabled)
				colorMan.getFlappySound().play();
		}
	}
	
	public GameEntity getTile(int x, int y){
		return gameEntitiesArray[y][x];
	}
	
	public GameEntity getTileFromPos(float x, float y){
		return gameEntitiesArray[(int)(x/getTileSize())][(int)(y/getTileSize())];
	}
	
	@Override
	public void dispose(){
		mainTheme.dispose();
	}

	@Override
	public void createAndStartMusic() {
		mainTheme = Gdx.audio.newMusic(Gdx.files.internal("assets/audio/playScreen2.mp3"));
		mainTheme.setVolume(0.1f);
		mainTheme.setLooping(true);
		mainTheme.play();
	}

	@Override
	public void activateSpecificActions() {
	}
	
	public ColorMan getColorMan(){
		return colorMan;
	}
	
	public void closePauseBoard(){
		if(!levelFinished && pauseBoard != null){
			pauseBoard = null;
			paused = false;
		}
	}
	
	public void createPauseBoard(){
		if(pauseBoard == null && !levelFinished){
			pauseBoard = new PauseBoard(this, level);
			paused = true;
		}
	}
	
}

