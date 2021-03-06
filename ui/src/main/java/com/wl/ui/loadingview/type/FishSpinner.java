package com.wl.ui.loadingview.type;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import com.wl.ui.loadingview.model.Circle;

/**
 * Created by Tuyen Nguyen on 2/12/17.
 */

public class FishSpinner extends LoaderView {
  private Circle[] circles;
  private int numberOfCircle;
  private float[] rotates;

  public FishSpinner() {
    numberOfCircle = 5;
    rotates = new float[numberOfCircle];
    valueAnimators = new ValueAnimator[numberOfCircle];
  }

  @Override
  public void initializeObjects() {
    final float size = Math.min(width, height);
    final float circleRadius = size / 10.0f;
    circles = new Circle[numberOfCircle];

    for (int i = 0; i < numberOfCircle; i++) {
      circles[i] = new Circle();
      circles[i].setCenter(center.x, circleRadius);
      circles[i].setColor(color);
      circles[i].setRadius(circleRadius - circleRadius * i / 6);
    }
  }

  @Override
  public void setUpAnimation() {
    for (int i = 0; i < numberOfCircle; i++) {
      final int index = i;

      valueAnimators[i] = ValueAnimator.ofFloat(0, 360);
      valueAnimators[i].setRepeatCount(ValueAnimator.INFINITE);
      valueAnimators[i].setDuration(1700);
      valueAnimators[i].setStartDelay(index * 100);
      valueAnimators[i].addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
          rotates[index] = (float)animation.getAnimatedValue();
          if (invalidateListener != null) {
            invalidateListener.reDraw();
          }
        }
      });

      valueAnimators[i].start();
    }
  }

  @Override
  public void draw(Canvas canvas) {
    for (int i = 0; i < numberOfCircle; i++) {
      canvas.save();
      canvas.rotate(rotates[i], center.x, center.y);
      circles[i].draw(canvas);
      canvas.restore();
    }
  }

}
