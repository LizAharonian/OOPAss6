import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.util.List;

/**
 * Game class.
 * responsible to initialize and run the game.
 */
public class GameLevel implements Animation {
    //members
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private Counter blocksCounter;
    private Counter ballsCounter;
    private Counter scoreCounter;
    private Counter numberOfLives;
    private GUI gui;
    private AnimationRunner runner;
    private boolean running;
    private KeyboardSensor keyboard;
    private LevelInformation levelInformation;



    /**
     * GameLevel function.
     * @param levelInformation - levelInformation obj.
     * @param keyboard - KeyboardSensor obj.
     * @param animationRunner - AnimationRunner obj, runs the game animation.
     * @param gui - GUI obj.
     * @param livesCounter - Counter obj.
     * @param scoreCounter - Counter obj.
     * @param blocksCounter - Counter obj.
     */
    GameLevel(LevelInformation levelInformation, KeyboardSensor keyboard, AnimationRunner animationRunner,
              GUI gui, Counter livesCounter, Counter scoreCounter, Counter blocksCounter) {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.blocksCounter = blocksCounter;
        this.ballsCounter = new Counter();
        this.scoreCounter = scoreCounter;
        numberOfLives = livesCounter;
        this.gui = gui;
        this.runner = animationRunner;
        this.keyboard = keyboard;
        this.levelInformation = levelInformation;
        this.sprites.addSprite(this.levelInformation.getBackground());

    }

    /**
     * addCollidable function.
     * adds collidable object to game environment.
     * @param c - collidable object.
     */

    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * addSprite function.
     * adds sprite object to game.
     * @param s - sprite object.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);

    }

    /**
     * initialize function.
     * Initialize a new game: create the Blocks and Ball
       and add them to the game.
     */
    public void initialize() {
        //listeners
        HitListener blockRemover = new BlockRemover(this, this.blocksCounter);
        HitListener scoreTrackingListener = new ScoreTrackingListener(this.scoreCounter);
        HitListener ballRemover = new BallRemover(this, this.ballsCounter);
        for (Block block : this.levelInformation.blocks()) {
            addExistBlockToGameWithListener(block, blockRemover, scoreTrackingListener);
        }

        //deathRegion block:
        Point point = new Point(0, 600);
        Rectangle rectangle = new Rectangle(point, 800, 20);
        Block deathRegion = new Block(rectangle, Color.RED, -100);
        deathRegion.addHitListener(ballRemover);
        deathRegion.addToGame(this);
        //add bounds blocks
        this.addGenericBlockToGame(0, 0, 20, 600, Color.gray);
        this.addGenericBlockToGame(0, 20, 800, 20, Color.gray);
        this.addGenericBlockToGame(780, 0, 20, 600, Color.gray);
        //score block:
        Point pointScore = new Point(0, 0);
        Rectangle rectangleScore = new Rectangle(pointScore, 800, 20);
        Block scoreRegion = new Block(rectangleScore, Color.white, -100);
        scoreRegion.addToGame(this);
        ScoreIndicator scoreIndicator = new ScoreIndicator(scoreRegion, Color.black, Color.WHITE, this.scoreCounter,
                this.levelInformation.levelName());
        this.sprites.addSprite(scoreIndicator);
        LivesIndicator livesIndicator = new LivesIndicator(scoreRegion, Color.black, this.numberOfLives);
        this.sprites.addSprite(livesIndicator);


    }

    /**
     * ddBallToGame function.
     * @param x - ball center position.
     * @param y - ball center position.
     * @param radius - ball's radius.
     * @param color - ball's color.
     * @param velocity - - ball's velocity.
     */
    private void addBallToGame(double x, double y, double radius, Color color, Velocity velocity) {
        Ball ball = new Ball((int) x, (int) y, (int) radius, color, environment);
        ball.setVelocity(velocity);
        ball.addToGame(this);
        this.ballsCounter.increase(1);

    }

    /**
     * addExistBlockToGameWithListener function.
     * @param block - Block obj.
     * @param hitListener -  block listener.
     * @param hitListener2 -  block listener.
     */
    private void addExistBlockToGameWithListener(Block block, HitListener hitListener, HitListener hitListener2) {
        block.addHitListener(hitListener);
        block.addHitListener(hitListener2);
        block.addToGame(this);
        this.blocksCounter.increase(1);
    }
    /**
     * addBlockToGame function.
     * @param x - upperLeft's point x position.
     * @param y - upperLeft's point y position.
     * @param width - block's width.
     * @param height - block's height.
     * @param color - block's color.
     */
    private void addGenericBlockToGame(double x, double y, double width, double height, Color color) {
        Point point = new Point(x, y);
        Rectangle rectangle = new Rectangle(point, width, height);
        Block block = new Block(rectangle, color, -100);
        block.addToGame(this);

    }

    /**
     * removeCollidable function.
     * @param c - collidable obj to remove from game.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * removeSprite function.
     * @param s - Sprite obj to remove from game.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);

    }

    /**
     * shouldStop function.
     * @return boolean.
     */
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * doOneFrame function.
     * @param d - DrawSurfaceObj for paint.
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        //pause screen
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY,
                    new PauseScreen(this.keyboard)));
        }
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt);
        if (this.blocksCounter.getValue() <= 0 || this.ballsCounter.getValue() <= 0) {
            this.running = false;
        }
        //decrease lives - no balls lefted
        if (this.ballsCounter.getValue() <= 0) {
            this.numberOfLives.decrease(1);
        }

    }

    /**
     * playOneTurn function.
     * runs one turn of game.
     */
    public void playOneTurn() {
        //create paddle
        Point pointPaddle = new Point(400 - (this.levelInformation.paddleWidth() / 2), 560);
        Rectangle rectangle5 = new Rectangle(pointPaddle, this.levelInformation.paddleWidth(), 20);
        Paddle paddle = new Paddle(rectangle5, Color.YELLOW, gui.getKeyboardSensor(),
                this.levelInformation.paddleSpeed());
        paddle.addToGame(this);
        this.createBallsOnTopOfPaddle(); // create balls
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));
        this.running = true;
        // use our runner to run the current animation -- which is one turn of
        // the game.
        this.runner.run(this);
        this.sprites.removeSprite(paddle);
        this.environment.removeCollidable(paddle);

    }

    /**
     * createBallsOnTopOfPaddle function.
     * creates balls according to levelInformation.
     */
    private void createBallsOnTopOfPaddle() {
        int numOfBalls = this.levelInformation.numberOfBalls();
        List<Velocity> velocities = this.levelInformation.initialBallVelocities();
        for (int i = 0; i < numOfBalls; i++) {
            this.addBallToGame(400, 540, 10, Color.BLUE, velocities.get(i));
        }
    }
}
