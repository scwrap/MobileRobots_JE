package kr.ac.uos.je.view.impl;

import java.nio.FloatBuffer;

import kr.ac.uos.je.accessories.OpenGLUtils;
import kr.ac.uos.je.model.EMapManager;
import kr.ac.uos.je.model.EObjectType;
import kr.ac.uos.je.model.interfaces.ResourceManager;
import kr.ac.uos.je.view.interfaces.DrawObject;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Area implements DrawObject {
	private final EObjectType objectType;
	private EMapManager mMapManager;

	public Area(EMapManager mMapManager, EObjectType objectType) {
		this.mMapManager = mMapManager;
		this.objectType = objectType;
	}


	private float[] pointVertices;
	private float[] color;
	private FloatBuffer pointVertexBuffer;
	@Override
	public void draw(Application app) {
		if(pointVertices == null && mMapManager.getMapStatus() == EMapManager.MapStatus.LoadingComplete){
			pointVertices = objectType.getVertices();
			if(pointVertices != null){
				pointVertexBuffer = OpenGLUtils.arrayToFloatBuffer(pointVertices);
				color = objectType.getColor();
			}
		}
		if(objectType.isColorChanged()){
			color = objectType.getColor();
		}
		if(pointVertices != null && objectType.isVisible()){
			GL10 gl = app.getGraphics().getGL10();
			gl.glLoadIdentity();
			gl.glPushMatrix();
			
			gl.glColor4f(color[0],color[1],color[2],color[3]);
			//Point to our vertex buffer
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, pointVertexBuffer);
			//Enable vertex buffer
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			//Draw the vertices as lines (1 line = 2 points)
			gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, pointVertices.length / 3);
			//Disable the client state before leaving
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glPopMatrix();
		}
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EObjectType getObjectType() {
		return this.objectType;
	}


	@Override
	public void update(Application app, SpriteBatch spriteBatch) {
		// TODO Auto-generated method stub
		
	}

}
