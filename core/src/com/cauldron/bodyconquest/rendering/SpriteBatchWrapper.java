package com.cauldron.bodyconquest.rendering;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;

public class SpriteBatchWrapper extends SpriteBatch {

    public SpriteBatchWrapper() {
        super();
    }

    @Override
    public void draw(Texture texture, float x, float y) {
        System.out.println("Does t go here");
        System.out.println(x*BodyConquest.scaleRatio);
        super.draw(texture, x*BodyConquest.scaleRatio, y*BodyConquest.scaleRatio);
    }

    @Override
    public void draw(Texture texture, float x, float y, float width, float height) {
        super.draw(texture, x*BodyConquest.scaleRatio, y*BodyConquest.scaleRatio, width, height);
    }

    @Override
    public void draw(Texture texture, float[] spriteVertices, int offset, int count) {
        super.draw(texture, spriteVertices, offset, count);
    }

    @Override
    public void draw(TextureRegion region, float width, float height, Affine2 transform) {
        super.draw(region, width, height, transform);
    }

    @Override
    public void draw(TextureRegion region, float x, float y, float width, float height) {
        super.draw(region, x*BodyConquest.scaleRatio, y*BodyConquest.scaleRatio, width, height);
    }

    @Override
    public void draw(TextureRegion region, float x, float y) {
        super.draw(region, x*BodyConquest.scaleRatio, y*BodyConquest.scaleRatio);
    }

    @Override
    public void draw(Texture texture, float x, float y, int srcX, int srcY, int srcWidth, int srcHeight) {
        super.draw(texture, x*BodyConquest.scaleRatio, y*BodyConquest.scaleRatio, srcX, srcY, srcWidth, srcHeight);
    }

    @Override
    public void draw(Texture texture, float x, float y, float width, float height, float u, float v, float u2, float v2) {
        super.draw(texture, x*BodyConquest.scaleRatio, y*BodyConquest.scaleRatio, width, height, u, v, u2, v2);
    }

    @Override
    public void draw(Texture texture, float x, float y, float width, float height, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY) {
        super.draw(texture, x*BodyConquest.scaleRatio, y*BodyConquest.scaleRatio, width, height, srcX, srcY, srcWidth, srcHeight, flipX, flipY);
    }

    @Override
    public void draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation) {
        super.draw(region, x*BodyConquest.scaleRatio, y*BodyConquest.scaleRatio, originX, originY, width, height, scaleX, scaleY, rotation);
    }

    @Override
    public void draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, boolean clockwise) {
        super.draw(region, x*BodyConquest.scaleRatio, y*BodyConquest.scaleRatio, originX, originY, width, height, scaleX, scaleY, rotation, clockwise);
    }

    @Override
    public void draw(Texture texture, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY) {
        super.draw(texture, x*BodyConquest.scaleRatio, y*BodyConquest.scaleRatio, originX, originY, width, height, scaleX, scaleY, rotation, srcX, srcY, srcWidth, srcHeight, flipX, flipY);
    }
}
