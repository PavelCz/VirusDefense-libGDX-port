package towerDefense;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import engine.GameComponent;
import engine.Level;
import engine.LevelHandler;
import engine.graphics.OwnSprite;
import engine.gui.SetGameModeAction;
import engine.gui.StaticText;

public class ChooseLevel extends GameComponent {

    private ImageButton levelSelectButton;
    private int pageNumber, lastPageNumber;
    private StaticText title = new StaticText(0, 0, 20, Color.BLACK, "Choose a level");

    private Level currentLevel;

    private LevelHandler levelHandler = new LevelHandler();

    public ChooseLevel(final TowerDefense game) {
        super(game);
        this.title.setPosition((TowerDefense.getWidth() - this.title.getWidth()) / 2, TowerDefense.getHeight() / 4);
        this.guiElements.add(this.title);
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
        OwnSprite currentPreviewPicture = this.currentLevel.getPreviewPicture();
        OwnSprite leftSprite = new OwnSprite("left.png", 0.07f);
        OwnSprite rightSprite = new OwnSprite("right.png", 0.07f);

        float leftX = TowerDefense.getWidth() / 4 - leftSprite.getWidth() / 2;
        float leftY = TowerDefense.getHeight() / 2 - leftSprite.getHeight() / 2;
        float rightX = TowerDefense.getWidth() - leftX;
        float rightY = leftY;
        float buttonX = TowerDefense.getWidth() / 2 - currentPreviewPicture.getWidth() / 2;
        float buttonY = TowerDefense.getHeight() / 2 - currentPreviewPicture.getHeight() / 2;

        float scale = 0.065f;

        Drawable drawable = new TextureRegionDrawable(currentPreviewPicture.getGDXSprite());
        this.levelSelectButton = new ImageButton(drawable);
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

        ImageButton leftButton = new ImageButton(new TextureRegionDrawable(leftSprite.getGDXSprite()), new TextureRegionDrawable(new OwnSprite("leftClicked.png", 0.065f).getGDXSprite()));
        leftButton.setX(leftX);
        leftButton.setY(leftY);
        leftButton.setScale(scale);
        leftButton.getImage().setScale(scale);
        leftButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeLevelSelection(-1);
            }
        });
        this.stage.addActor(leftButton);

        ImageButton rightButton = new ImageButton(new TextureRegionDrawable(rightSprite.getGDXSprite()), new TextureRegionDrawable(new OwnSprite("rightClicked.png", 0.065f).getGDXSprite()));
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
        if(this.pageNumber < 0) {
            this.pageNumber = this.lastPageNumber;
        } else if(this.pageNumber > this.lastPageNumber) {
            this.pageNumber = 0;
        }
        this.currentLevel = this.levelHandler.get(this.pageNumber);

        // Update the preview picture shown on the level select button
        OwnSprite currentPreviewPicture = this.currentLevel.getPreviewPicture();
        // Change the image of the button
        this.levelSelectButton.getStyle().imageUp = new TextureRegionDrawable(currentPreviewPicture.getGDXSprite());
    }

    private Level getCurrentLevel() {
        return this.currentLevel;
    }

}
