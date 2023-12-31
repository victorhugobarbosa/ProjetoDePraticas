package com.example.cotucatdpd.graphics

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import com.example.cotucatdpd.R

class SpriteSheet(context: Context?) {
    private val SPRITE_WIDTH_PIXELS = 64
    private val SPRITE_HEIGHT_PIXELS = 64
    private var bitmapOptions = BitmapFactory.Options()
    private var bitmap: Bitmap?

    init{
        bitmapOptions.inScaled = false
        bitmap = BitmapFactory.decodeResource(context!!.resources, R.drawable.sprite_sheet, bitmapOptions)
    }

    fun getPlayerSpriteArray(): Array<Sprite?> {
        val spriteList = arrayOfNulls<Sprite>(3)
        spriteList[0] = Sprite(this, Rect(0*64, 0, 1*64, 64))
        spriteList[1] = Sprite(this, Rect(1*64, 0, 2*64, 64))
        spriteList[2] = Sprite(this, Rect(2*64, 0, 3*64, 64))
        //return Sprite(this, Rect(0, 0, 320, 320))
        return spriteList
    }

    fun getInimigosSpriteArray(): Array<Sprite?> {
        val spriteList = arrayOfNulls<Sprite>(12)
        //Olhando para cima
        spriteList[0] = Sprite(this, Rect(0*64, 2*64, 1*64, 64*3))
        spriteList[1] = Sprite(this, Rect(1*64, 2*64, 2*64, 64*3))
        spriteList[2] = Sprite(this, Rect(2*64, 2*64, 3*64, 64*3))
        //Olhando para a direita
        spriteList[3] = Sprite(this, Rect(0*64, 3*64, 1*64, 64*4))
        spriteList[4] = Sprite(this, Rect(1*64, 3*64, 2*64, 64*4))
        spriteList[5] = Sprite(this, Rect(2*64, 3*64, 3*64, 64*4))
        //Olhando para baixo
        spriteList[6] = Sprite(this, Rect(0*64, 4*64, 1*64, 64*5))
        spriteList[7] = Sprite(this, Rect(1*64, 4*64, 2*64, 64*5))
        spriteList[8] = Sprite(this, Rect(2*64, 4*64, 3*64, 64*5))
        //Olhando para esquerda
        spriteList[9] = Sprite(this, Rect(0*64, 5*64, 1*64, 64*6))
        spriteList[10] = Sprite(this, Rect(1*64, 5*64, 2*64, 64*6))
        spriteList[11] = Sprite(this, Rect(2*64, 5*64, 3*64, 64*6))
        return spriteList
    }

    fun getBitmap(): Bitmap? {
        return bitmap
    }
    fun getGroundSprite(): Sprite {
        return getSpriteByIndex(1, 0)
    }

    private fun getSpriteByIndex(idxRow: Int, idxCol: Int): Sprite {
        return Sprite(this, Rect(
                idxCol * SPRITE_WIDTH_PIXELS,
                idxRow * SPRITE_HEIGHT_PIXELS,
                (idxCol + 1) * SPRITE_WIDTH_PIXELS,
                (idxRow + 1) * SPRITE_HEIGHT_PIXELS
        ))
    }
}