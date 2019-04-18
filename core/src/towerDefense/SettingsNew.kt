package towerDefense

import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import engine.GameComponent
import engine.gui.SetGameModeAction
import ktx.actors.onChange

class SettingsNew (val g:TowerDefense) : GameComponent (g) {

    init {
        val textButtonStyle = game.textButtonStyle
        val back = TextButton("Back", textButtonStyle)
        back.x = 0f
        back.y = back.height * 2
        back.onChange { game.setMode(TowerDefense.MODE_MENU) }
    }
}