package com.example.cotucatdpd

import android.graphics.Canvas
import android.util.Log
import android.view.SurfaceHolder

class GameLoop(game: Game, surfaceHolder: SurfaceHolder) : Thread() {

    private val game: Game
    private val surfaceHolder: SurfaceHolder
    private var isRunning = false
    var averageUPS = 0.0
        private set
    var averageFPS = 0.0
        private set

    init {
        this.game = game
        this.surfaceHolder = surfaceHolder
    }

    fun startLoop() {
        isRunning = true
        start()
    }

    override fun run() {
        super.run()

        // Declare time and cycle count variables
        var updateCount = 0
        var frameCount = 0
        var startTime: Long
        var elapsedTime: Long
        var sleepTime: Long

        // Game loop
        var canvas: Canvas? = null
        startTime = System.currentTimeMillis()
        while (isRunning) {

            // Try to update and render game
            try {
                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    game.update()
                    updateCount++
                    if(canvas != null)
                        game.draw(canvas)
                }
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                        frameCount++
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            // Pause game loop to not exceed target UPS
            elapsedTime = System.currentTimeMillis() - startTime
            sleepTime = (updateCount * UPS_PERIOD - elapsedTime).toLong()
            if (sleepTime > 0) {
                try {
                    sleep(sleepTime)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }

            // Skip frames to keep up with target UPS
            while (sleepTime < 0 && updateCount < MAX_UPS - 1) {
                game.update()
                updateCount++
                elapsedTime = System.currentTimeMillis() - startTime
                sleepTime = (updateCount * UPS_PERIOD - elapsedTime).toLong()
            }

            // Calculate average UPS and FPS
            elapsedTime = System.currentTimeMillis() - startTime
            if (elapsedTime >= 1000) {
                averageUPS = updateCount / (1E-3 * elapsedTime)
                averageFPS = frameCount / (1E-3 * elapsedTime)
                updateCount = 0
                frameCount = 0
                startTime = System.currentTimeMillis()
            }
        }
    }

    fun stopLoop() {
        isRunning = false
        try {
            join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    companion object {
        const val MAX_UPS = 30.0
        private const val UPS_PERIOD = 1E+3 / MAX_UPS
    }
}