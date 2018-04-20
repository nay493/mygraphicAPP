import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class ball_bouncing extends PApplet {


//PGraphics pg;
// Example 10-2: Bouncing ball class

//font
PFont f;
float theta;
//font

//blue Wave
float freq = 10;
float amp = 50;
float w = 20;
float h = 20;
//blue Wave

// smoke
int size = 1024;
int siz = size-1;
int si = size-2;

float halfWidth, halfHeight;

float r = 192, g = 192, b = 192;
float vr, vg, vb;

float tension = 0.5f;
float sympathy = 0.25f;

float[] px = new float[size];
float[] py = new float[size];
float[] vx = new float[size];
float[] vy = new float[size];
float[] ax = new float[size];
float[] ay = new float[size];


// Two ball variables

//public class Ballext extends Ball{
Ball ball1;
Ball ball2;
int fillVal = color(126);
int x;
Drop[] drops = new Drop[300];
PImage a,bb,img;
PImage c;
//##TODO
//img = c ??

public void setup() {
  
 // f = createFont("Arial",16,true);
  f = createFont("Ziggurat-Black", 32);
  //blue Wave
  //size(640, 450);
  background(255);
  rectMode(CENTER);
  //noStroke();
//blue Wave
  
  //Image handling
  a = loadImage("svg1.jpeg");
  bb = loadImage("koliberek-Key.svg");
  c = loadImage("image2.jpeg");
  //Image handling
  //smoke
    
  background(0);
  noFill();
  strokeWeight(0.05f);
  
  halfWidth = width/2;
  halfHeight = height/2;
  
  for (int i = 0; i < size; i++) {
    float angle = TAU * i / size;
    px[i] = halfWidth + cos(angle) * halfHeight;
    py[i] = halfHeight + sin(angle) * halfHeight;
  }
  //smoke
  //size(480, 270);
  surface.setResizable(true);
  surface.setTitle("Window-1");
 
  // Initialize balls
  ball1 = new Ball(64);
  ball2 = new Ball(32);
  for(int i=0;i<300;i++)
  {
    drops[i] = new Drop();
  }
}

 

public void draw(){


  background(fillVal);
  
  
  
  textFont(f,18);                  // STEP 3 Specify font to be used
  fill(0);  // STEP 4 Specify font color
  //rotate(theta);
  beginShape();
vertex(50, 15);
bezierVertex(50, -5, 90, 5, 50, 40);
vertex(50, 15);
bezierVertex(50, -5, 10, 5, 50, 40);
endShape();
  
  text("Welcome All",width/2,height/2);   // STEP 5 Display Text
  text("Please Enter Keys 1. Up 2. Down 3. Enter To see the Magic",1.2f*(width/2),1.2f*(height/2));
  text("V", 0, 60);
text("O", 15, 65);
text("T", 30, 70);
text("A", 45, 75);
text("R", 60, 80);
text("Y", 75, 85);
//text("   ", 75, 85);
text("T", 90, 90);
text("E", 105, 95);
text("C", 120, 100);
text("H", 135, 105);
//text("N", 150, 110);
//text("A", 165, 115);
  //theta += 0.05;
   // Move and display balls
  ball1.move();
  ball2.move();
  ball1.display();
  ball2.display();
   for(int i=0;i<300;i++)
  {
  drops[i].fall();
  drops[i].show();
  }
  //smoke
 for (int i = 1; i < siz; i++) {
    ax[i] = (px[i-1] + px[i+1] - px[i] - px[i]) * tension + (vx[i-1] + vx[i+1] - vx[i] - vx[i]) * sympathy;
    ay[i] = (py[i-1] + py[i+1] - py[i] - py[i]) * tension + (vy[i-1] + vy[i+1] - vy[i] - vy[i]) * sympathy;
  }
  
  ax[0] = (px[siz] + px[1] - px[0] - px[0]) * tension + (vx[siz] + vx[1] - vx[0] - vx[0]) * sympathy;
  ay[0] = (py[siz] + py[1] - py[0] - py[0]) * tension + (vy[siz] + vy[1] - vy[0] - vy[0]) * sympathy;
  
  ax[siz] = (px[si] + px[0] - px[siz] - px[siz]) * tension + (vx[si] + vx[0] - vx[siz] - vx[siz]) * sympathy;
  ay[siz] = (py[si] + py[0] - py[siz] - py[siz]) * tension + (vy[si] + vy[0] - vy[siz] - vy[siz]) * sympathy;
  
  int randomNode = PApplet.parseInt(random(size));
  ax[randomNode] = (halfWidth - px[randomNode]) * 0.001f + randomGaussian() * 5;
  ay[randomNode] = (halfHeight - py[randomNode]) * 0.001f + randomGaussian() * 5;
  
  for (int i = 0; i < size; i++) {
    vx[i] += ax[i];
    vy[i] += ay[i];
    px[i] += vx[i];
    py[i] += vy[i];
    px[i] = constrain(px[i], 0, width);
    py[i] = constrain(py[i], 0, height);
  }
  
  vr = vr * 0.995f + randomGaussian() * 0.04f;
  vg = vg * 0.995f + randomGaussian() * 0.04f;
  vb = vb * 0.995f + randomGaussian() * 0.04f;
  
  r += vr;
  g += vg;
  b += vb;
  
  if ((r < 128 && vr < 0) || (r > 255 && vr > 0))    vr = -vr;
  if ((g < 128 && vg < 0) || (g > 255 && vg > 0))    vg = -vg;
  if ((b < 128 && vb < 0) || (b > 255 && vb > 0))    vb = -vb;
  
  stroke(r, g, b);
  
  beginShape();
  for (int i = 0; i < size; i++) {
    vertex(px[i], py[i]);
  }
  endShape();
//smoke
  imageMode(CENTER);
  image(a,width/2,height/2);

  //blue Wave
fill(0, 0, 50, 40);
  rect(0, 0, width*2, height*2);
   
  for (int i = 0; i < 33; i++) {
    // Middle blue
    fill(220, 255, 255, 255);
    rect(i*20, (height/2)+sin((frameCount+i*3)/freq)*(amp*1), w, h, 50);
     
    for (int x = 1; x < 8; x++) {
      // Top blue
      fill(230-(x*40), 255, 255, 255-(x*32));
      rect(i*20, (height/2-(x*20)) + sin((frameCount+i*3)/freq) * (amp*(1-(x*0.13f))), w*(1-(x*0.075f)), h*(1-(x*0.075f)), 25-(x*3.5f));
       
      // Bottom blue
      fill(230-(x*40), 255, 255, 255-(x*32));
      rect(i*20, (height/2+(x*20)) + sin((frameCount+i*3)/freq) * (amp*(1-(x*0.13f))), w*(1-(x*0.075f)), h*(1-(x*0.075f)), 25-(x*3.5f));
       
      // Top purple
      fill(255, 230-(x*20), 255, 15);
      rect(i*20+10, (height/2-(x*20)) + cos((frameCount+i*3)/freq) * (amp*(1-(x*0.13f))), w*(x*0.125f), h*(x*0.125f), 25-(x*3.5f));
       
      // Bottom purple
      fill(255, 200-(x*20), 255, 15);
      rect(i*20+10, (height/2+(x*20)) + cos((frameCount+i*3)/freq) * (amp*(1-(x*0.13f))), w*(x*0.125f), h*(x*0.125f), 25-(x*3.5f));
    }
  }
//blue Wave
 

}
//}
 public void mousePressed(){
    background(100);
    fill(255);
    stroke(255);
    rectMode(CENTER);
    rect(mouseX,mouseY,30,30);
  }
  
public void keyPressed() {
  if (key == CODED) {
    if (keyCode == UP) {
      fillVal = 255;
      img = a;
    } else if (keyCode == DOWN) {
      fillVal = 0;
      img = bb;
    } else if (keyCode == ENTER) {
      x = 0;
      img = c;
    } 
  } else {
    fillVal = 126;
  }
}
 
public void mouseMoved() {
  freq = 10 * (1.0f-mouseX/(float)width+1);
  amp = 50 * (1.0f-mouseY/(float)height+1);
} 


// Example 10-2: Bouncing ball class

public class Ball {

  float r;   // radius
  float x, y; // location
  float xspeed, yspeed; // speed

    // Constructor
  Ball(float tempR) {
    r = tempR;
    x = random(width);
    y = random(height);
    xspeed = random( - 5, 5);
    yspeed = random( - 5, 5);
  }

  public void move() {
    x += xspeed; // Increment x
    y += yspeed; // Increment y

    // Check horizontal edges
    if (x > width || x < 0) {
      xspeed *= -1;
    }
    //Check vertical edges
    if (y > height || y < 0) {
      yspeed *= -1;
    }
  }

  // Draw the ball
  public void display() {
    stroke(0);
    fill(0, 150);
    //fill(a);
    ellipse(x, y, r*2, r*2);
  }
}
class Drop{
float x = random(width);
float y = random(-200,-100);
float yspeed = random(5,15);
float len = random(5,15);
public void fall()
{
  y = y + yspeed;
  if (y>height)
  {
   y = random(-200,-100);
  }
}

public void show()
{
  stroke(138,43,226);
  line(x,y,x,y+len);
}

}
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "ball_bouncing" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
