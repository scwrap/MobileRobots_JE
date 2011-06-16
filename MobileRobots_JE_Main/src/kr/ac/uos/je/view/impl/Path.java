package kr.ac.uos.je.view.impl;

import java.nio.FloatBuffer;

import kr.ac.uos.je.model.EMapManager;
import kr.ac.uos.je.model.EObjectType;
import kr.ac.uos.je.model.interfaces.ResourceManager;
import kr.ac.uos.je.utils.OpenGLUtils;
import kr.ac.uos.je.view.interfaces.DrawObject;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Path implements DrawObject {
	private final EObjectType objectType;
	private EMapManager mMapManager;

	public Path(EMapManager mMapManager, EObjectType objectType) {
		this.mMapManager = mMapManager;
		this.objectType = objectType;
	}


	private float[] pointVertices;
	private float[] color;
	private FloatBuffer pointVertexBuffer;
	@Override
	public void draw(Application app) {
		if(mMapManager.getMapStatus() == EMapManager.MapStatus.LoadingComplete){
			if(objectType.isColorChanged()){
				color = objectType.getColor();
			}
		
			if(objectType.isVisible()){
			  
				pointVertices = objectType.getVertices();
				if(pointVertices != null){
					pointVertexBuffer = OpenGLUtils.arrayToFloatBuffer(pointVertices);
					
					GL10 gl = app.getGraphics().getGL10();
					gl.glLoadIdentity();
					gl.glPushMatrix();
					
					gl.glColor4f(color[0],color[1],color[2],color[3]);
					//Point to our vertex buffer
					gl.glVertexPointer(3, GL10.GL_FLOAT, 0, pointVertexBuffer);
					//Enable vertex buffer
					gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
					//Draw the vertices as lines (1 line = 2 points)
					gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, pointVertices.length / 3);
					//Disable the client state before leaving
					gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
					gl.glPopMatrix();
				}
			}
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
