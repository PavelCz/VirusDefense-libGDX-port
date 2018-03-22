package towerDefense;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import engine.GameComponent;
import engine.Level;
import engine.LevelHandler;
import engine.gui.SetGameModeAction;

public class ChooseLevel extends GameComponent {

    private ImageButton levelSelectButton;
    private int pageNumber, lastPageNumber;
    private Label title;

    private Level currentLevel;

    private LevelHandler levelHandler = new LevelHandler();

    public ChooseLevel(final TowerDefense game) {
        super(game);

        this.title = new Label("Choose a level", this.game.getLabelStyle());
        this.title.setPosition((TowerDefense.getWidth() - this.title.getWidth()) / 2, TowerDefense.getHeight() / 4);
        this.stage.addActor(this.title);
        this.pageNumber = 0;
        this.levelHandler.add("level1.txt", game.getGameplay());
        this.levelHandler.add("level4.txt", game.getGameplay());
        this.levelHandler.add("level2.txt", game.getGameplay());
        // this.levelHandler.add("level3.txt", game.getGameplay());

        this.currentLevel = this.levelHandler.get(this.pageNumber);

        initializeButtons(game);

        this.lastPageNumber = this.levelHandler.getLength() - 1;
    }

    private void initializeButtons(final TowerDefense game) {
        float scale = 0.065f;

        this.levelSelectButton = new ImageButton(this.currentLevel.getPreviewPictureDrawable());
        float buttonX = TowerDefense.getWidth() / 2 - levelSelectButton.getWidth() / 2;
        float buttonY = TowerDefense.getHeight() / 2 - levelSelectButton.getHeight() / 2;
        this.levelSelectButton.setX(buttonX);
        this.levelSelectButton.setY(buttonY);
        this.levelSelectButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.initGameplay(getCurrentLevel());
                game.getMenu().setDisableTextField(true);
                game.getGameplay().setPlayerName(game.getPlayerName());
                game.setMode(TowerDefense.MODE_GAME);
            }
        });
        this.stage.addActor(this.levelSelectButton);
        String imagePath = "data/graphics/";
        ImageButton leftButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(imagePath + "left.png"))), new TextureRegionDrawable(new TextureRegion(new Texture(imagePath + "leftClicked.png"))));
        leftButton.setScale(scale);
        leftButton.getImage().setScale(scale);
        float leftX = TowerDefense.getWidth() / 4 - leftButton.getImage().getWidth() / 2;
        float leftY = TowerDefense.getHeight() / 2 - leftButton.getImage().getHeight() / 2;
        System.out.println("JO: " + leftX);
        System.out.println("2: " + TowerDefense.getWidth());
        System.out.println("JO: " + leftY);
        System.out.println("2: " + leftButton.getHeight());
        leftButton.setX(leftX);
        leftButton.setY(leftY);
        leftButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeLevelSelection(-1);
            }
        });
        this.stage.addActor(leftButton);

        ImageButton rightButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(imagePath + "right.png"))), new TextureRegionDrawable(new TextureRegion(new Texture(imagePath + "rightClicked.png"))));
        float rightX = TowerDefense.getWidth() - leftX;
        float rightY = leftY;
        rightButton.setX(rightX);
        rightButton.setY(rightY);
        rightButton.setScale(scale);
        rightButton.getImage().setScale(scale);
        rightButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeLevelSelection(1);
            }
        });
        this.stage.addActor(rightButton);

        TextButtonStyle textButtonStyle = this.game.getTextButtonStyle();

        TextButton back = new TextButton("Back", textButtonStyle);
        back.setX(0);
        back.setY(0 + back.getHeight() * 2);
        back.addListener(new SetGameModeAction(this.game, TowerDefense.MODE_MENU));
        this.stage.addActor(back);
    }

    private void changeLevelSelection(int amount) {
        this.pageNumber += amount;
        if (this.pageNumber < 0) {
            this.pageNumber = this.lastPageNumber;
        } else if (this.pageNumber > this.lastPageNumber) {
            this.pageNumber = 0;
        }
        this.currentLevel = this.levelHandler.get(this.pageNumber);

        // Update the preview picture shown on the level select button;
        // Change the image of the button
        this.levelSelectButton.getStyle().imageUp = this.currentLevel.getPreviewPictureDrawable();
    }

    private Level getCurrentLevel() {
        return this.currentLevel;
    }

}
