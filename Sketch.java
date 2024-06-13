import processing.core.PApplet;
/**
 * simulates a snowfalling game, the player loses a life everytime it collides with the snowflake. Snowflakes will disappear when clicked on. This game ends when all lives are lost. 
 * @author: J. He
 *
 */
public class Sketch extends PApplet {

  // arrays of the (x, y) coordinate for the snowflakes
  float[] snowX = new float[40];
  float[] snowY = new float[40];

  // Array for hiding snowflakes (disappear) 
  boolean[] blnHideSnow = new boolean[40];

  // size of snowflakes
  int snowDiameter = 15;

  boolean blnUpPressed = false;
  boolean blnDownPressed = false;
  boolean blnWPressed = false;
  boolean blnSPressed = false;
  boolean blnAPressed = false;
  boolean blnDPressed = false;
  boolean blnHit = false;

  // Initializing speed variables
  float fltCircleX = 0;
  float fltCircleY = 0;
  float fltPlayerX = 10;
  float fltPlayerY = 200;

  // Defining variables
  int intLocation = 0;
  int intPlayerLives = 3;
  double dblClickingRadius = 20;
  double dblRadius = 12;

  public void settings() {
    size(400, 400);
  }

  public void setup() {
    background(0);
   
    // Random x and y values/location of snowflake 
    for(int i = 0; i < snowX.length; i++) {
      snowX[i] = random(width);
      snowY[i] = random(height);
    }
  }

  /**
  * Creates player, snowfall, and lives (heart shape) 
  *
  * @return void
  * @author: J. He
  */
  public void draw() {
    background(0);

    // Add hearts for life
    fill(255, 79, 20);
    if (intPlayerLives == 3) {
      square(370, 0, 30);
      square(370, 30, 30);
      square(370, 60, 30);
    } else if (intPlayerLives == 2) {
      square(370, 0, 30);
      square(370, 30, 30);
    } else if (intPlayerLives == 1) {
      square(370, 0, 30);
    }

    if(key == 'w' || key == 'W' || key == 's' || key == 'S' || key == 'a' || key == 'A' || key == 'd' || key == 'D' || keyCode == DOWN || keyCode == UP) {
      movingPlayer();
    }
   
    if (blnHit == true) {
      blnHideSnow[intLocation] = true;
    }

    // Locate position of all snowflake
    for (int i = 0; i < 40; i++) {  
      // Sends touched snowflakes back up if player touches snowflake
      if (dist(fltPlayerX, fltPlayerY, snowX[i], snowY[i]) <= dblRadius) {
        intPlayerLives -= 1;
        snowY[i] = 0;
      }
    }
   
    // Draw snowflakes
    snow();

    // Creating boundaries
    if (fltPlayerX < 10) {
      fltPlayerX = 10;
    } else if (fltPlayerX > width - 10) {
      fltPlayerX = width - 10;
    } else if (fltPlayerY < 10) {
      fltPlayerY = 10;
    } else if (fltPlayerY > 390) {
      fltPlayerY = 390;
    }
   
    // End game 
    if (intPlayerLives == 0) {
      background(255);
      fill(255, 0, 0);
      textSize(40);
      text("Game Over!", 100, 200);
      }
    }

  /**
  * WASD key for movements of player
  *
  * @return void
  * @author: J. He
  */
  private void movingPlayer() {
    if (blnWPressed) {
      fltPlayerY -= 2;
    }
    if (blnSPressed ) {
      fltPlayerY += 2;
    }
    if (blnAPressed) {
      fltPlayerX -= 2;
    }
    if (blnDPressed) {
      fltPlayerX += 2;
    }

    // Add player representative
    fill(128, 191, 242);
    circle(fltPlayerX, fltPlayerY, 20);
  }

  /**
  * Spawns snow and brings it back to top for each new cycle. Make snow fall faster/slower when arrow up and down keys are pressed.
  *
  * @return void
  * @author: J. He
  */
  public void snow(){
    fill(255);
    for(int i = 0; i < snowX.length; i++) {
      if (!blnHideSnow[i]) {
      circle(snowX[i], snowY[i], snowDiameter);
      snowY[i] += 2;

        // Reset snowflakes
        if (snowY[i] > height) {
          snowY[i] = 0;
        }

        // Check if snowflake is clicked
        if (mousePressed) {
          if (dist(snowX[i], snowY[i], mouseX, mouseY) < snowDiameter / 2) {
            blnHideSnow[i] = true;
          }
        }

        // Make snow fall faster
        if(blnDownPressed) {
          circle(snowX[i], snowY[i], snowDiameter);
          snowY[i] += 5;
        } else if(blnUpPressed) {
          circle(snowX[i], snowY[i], snowDiameter);
          snowY[i] --;
        }
      }
    }
  }

  /**
  * Sets up/down/W/A/S/D to 'true' when keys pressed
  *
  * @return void
  * @author: J. He
  */
  public void keyPressed() {
    if (keyCode == DOWN) {
      blnDownPressed = true;
    }
    else if (keyCode == UP) {
      blnUpPressed = true;
    }
    if (key == 'w' || key == 'W') {
      blnWPressed = true;
    }
    else if (key == 's' || key == 'S') {
      blnSPressed = true;
    }
    else if (key == 'a' || key == 'A') {
      blnAPressed = true;
    }
    else if (key == 'd' || key == 'D') {
      blnDPressed = true;
    }
  }

  /**
  * Sets  up/down/W/A/S/D keys to 'false' when released
  *
  * @return void
  * @author: J. He
  */
  public void keyReleased() {
    if (keyCode == DOWN) {
      blnDownPressed = false;
     }
    else if (keyCode == UP) {
      blnUpPressed = false;
    }

    if (key == 'w' || key == 'W') {
      blnWPressed = false;
    }
    else if (key == 's' || key == 'S') {
      blnSPressed = false;
    }
    else if (key == 'a' || key == 'A') {
      blnAPressed = false;
    }
    else if (key == 'd' || key == 'D') {
      blnDPressed = false;
    }
  }

  /**
  * Makes snowflakes disappear when clicked on
  *
  * @return void
  * @author: J. He
  */
  public void mousePressed() {
    for (int i = 0; i < 40; i++) {
      if (dist(mouseX, mouseY, snowX[i], snowY[i]) < dblClickingRadius) {
        blnHit = true;
      }
    }
  }
}