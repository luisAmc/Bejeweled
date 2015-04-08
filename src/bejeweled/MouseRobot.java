/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bejeweled;

/**
 *
 * @author Luis Martinez
 */
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;


public class MouseRobot extends Robot implements Runnable {
        
        private int activeWaitTime = 30;
        private int inactiveWaitTime = 100;
        
        public boolean active = false;
        private float progress = 0.0f;
        private final float moveSpeed = 10.0f;
        private long time;
        
        private Point startPoint;
        private Point endPoint;
        
        private Point mousePos;
        
        private enum MouseState {released, pressed};
        private MouseState mouseState = MouseState.released;
        
        public MouseRobot() throws AWTException {
        super();
    }

        public void run() {
            while(true) {
                if(active) {
                    if(mouseState.equals(MouseState.pressed)) {
                            long newTime = System.currentTimeMillis();
                            progress += ((newTime - time) * moveSpeed) / 1000.0f;
                            if(progress > 1.0f) {
                                mouseMove(endPoint.x, endPoint.y);
                                mouseRelease(InputEvent.BUTTON1_MASK);
                                mouseState = MouseState.released;
                                active = false;
                            } else {
                                time = System.currentTimeMillis();
                                mousePos.x = (int) (startPoint.x + (endPoint.x - startPoint.x) * progress);
                                mousePos.y = (int) (startPoint.y + (endPoint.y - startPoint.y) * progress);
                                mouseMove(mousePos.x, mousePos.y);
                            }
                    } else if(mouseState.equals(MouseState.released)) {
                        mouseMove(startPoint.x, startPoint.y);
                        mousePos.x = startPoint.x;
                        mousePos.y = startPoint.y;
                        mousePress(InputEvent.BUTTON1_MASK);
                        time = System.currentTimeMillis();
                        mouseState = MouseState.pressed;
                    }
                    try {
                        Thread.sleep(activeWaitTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Thread.sleep(inactiveWaitTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        
        public void MakeMove(Point _startPoint, Point _endPoint) {
            if(!active) {
                mousePos = new Point(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
                startPoint = _startPoint;
                endPoint = _endPoint;
                progress = 0.0f;
                time = System.currentTimeMillis();
                mouseState = MouseState.released;
                active = true;
            }
        }

}