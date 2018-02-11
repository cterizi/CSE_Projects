#include <Windows.h>
#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <GL/gl.h>
#include <GL/glu.h>
#include <GL/glut.h>

#pragma warning (disable : 4996)

#define RED 0
#define BLUE 1
#define YELLOW 2
#define PINK 3
#define MAGENTA 4
#define BLOCK_SIZE 1.0

void drawText(const char *text, int length, int x, int y);
void display(void);
void changeSize(int w, int h);
void processNormalKeys(unsigned char key, int x, int y);
void initializeGrid();
void drawSquare(int x, int y);
void drawBottom(int i, int j, int l);
void init();

int posX = 200, posY = 50, winWidth = 670, winHeight = 550;
int gridSize; 
bool gives_input;
bool gameStart;
int ***mapColor;
int tx1, ty1, tx2, ty2, tx3, ty3, tx4, ty4;
GLdouble eyeX, eyeY, eyeZ, centerX, centerY, centerZ;

int main(int argc, char **argv){
	//init Glut and create window
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
	glutInitWindowPosition(posX, posY);
	glutInitWindowSize(winWidth, winHeight);
	glutCreateWindow("Pink-MineCraft");

	//Call init function
	init();

	//register callbacks
	glutDisplayFunc(display);
	glutReshapeFunc(changeSize);
	glutIdleFunc(display);

	//Keyboard functions
	glutKeyboardFunc(processNormalKeys);
	//glutSpecialFunc(processSpecialKeys);

	// OpenGL init
	glEnable(GL_DEPTH_TEST);

	//enter Glut event processing cyclei
	glutMainLoop();
}

void init(){
	gridSize = 0;
	gives_input = true;
	gameStart = false;
	mapColor = (int ***)malloc(gridSize*sizeof(int **));
	for (int i = 0; i < gridSize; i++) {
		mapColor[i] = (int **)malloc(gridSize*sizeof(int *));
		for (int j = 0; j < gridSize; j++) {
			mapColor[i][j] = (int *)malloc(gridSize*sizeof(int));
		}
	}


	tx1 = -2; ty1 = 2;
	tx2 = tx1 + BLOCK_SIZE; ty2 = ty1;
	tx3 = tx2; ty3 = ty2 - BLOCK_SIZE;
	tx4 = tx2 - BLOCK_SIZE; ty4 = ty3;

	eyeX = 1.4; 
	eyeY = 5 + gridSize*BLOCK_SIZE + BLOCK_SIZE*gridSize; 
	eyeZ = 5.0;
	centerX = 1.4, centerY = 1.0; 
	centerZ = 4;
}

void display(void) {

	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glLoadIdentity();

	if (gives_input){
		glDisable(GL_DEPTH_TEST);
		char s[11], temp[40]; //converts moves_input to string
		sprintf(s, "%d", gridSize);
		strcpy(temp, "Give size of grid: ");
		strcat(temp, s);
		drawText(temp, strlen(temp), winWidth / 2 - winWidth / 3.4 + 95, winHeight / 2 - 20);
		drawText("Press c to clear the input", strlen("Press c to clear the input"), winWidth / 2 - winWidth / 3.4 + 80, winHeight / 2 - 60);
		drawText("Press e to start the game", strlen("Press e to start the game"), winWidth / 2 - winWidth / 3.4 + 85, winHeight / 2 - 85);
		glEnable(GL_DEPTH_TEST);
	}
	else{
		//Game play

		gluLookAt(eyeX, eyeY, eyeZ,
			centerX, centerY, centerZ,
			0.0f, 1.0f, 0.0f);


		glColor3f(1.0, 0.0, 0.0);
		drawBottom(1, 1, 1);
		
	}

	glutSwapBuffers();

}

void initializeGrid(){
	for (int i = 0; i < gridSize; i++){
		for (int j = 0; j < gridSize; j++){
			for (int k = 0; k < gridSize; k++){
				//mapColor[i][j][k] = 0;
			}
		}
	}
}

void drawBottom(int i, int j, int l){
	glEnable(GL_BLEND);
	glBegin(GL_QUADS);
	glTexCoord2f(0.0f, 0.0f);
	glVertex3f(tx1 + i*BLOCK_SIZE, BLOCK_SIZE * l, ty1 + j*BLOCK_SIZE);
	glTexCoord2f(1.0f, 0.0f);
	glVertex3f(tx2 + i*BLOCK_SIZE, BLOCK_SIZE * l, ty2 + j*BLOCK_SIZE);
	glTexCoord2f(1.0f, 1.0f);
	glVertex3f(tx3 + i*BLOCK_SIZE, BLOCK_SIZE * l, ty3 + j*BLOCK_SIZE);
	glTexCoord2f(0.0f, 1.0f);
	glVertex3f(tx4 + i*BLOCK_SIZE, BLOCK_SIZE * l, ty4 + j*BLOCK_SIZE);
	glEnd();
	glDisable(GL_BLEND);
}


/*
void drawSquare(int x, int y){
	glBegin(GL_QUADS);
	glVertex2f(x, y);
	glVertex2f(x + BLOCK_SIZE, y);
	glVertex2f(x + BLOCK_SIZE, y + BLOCK_SIZE);
	glVertex2f(x, y + BLOCK_SIZE);
	glEnd();
}*/

void processNormalKeys(unsigned char key, int x, int y) {

	if (key == 27)
		exit(0);
	else if (key == 'e' && gives_input){
		if (gridSize <= 100000){
			gives_input = false;
			gameStart = true;
			initializeGrid();
		}
		else{
			gridSize = 0;
		}
	}
	else if (key == 'c' && gives_input){
		gridSize = 0;
	}
	//this is for input size_grid
	else if (key == '1' && gives_input){
		gridSize = gridSize * 10 + 1;
	}
	else if (key == '2' && gives_input){
		gridSize = gridSize * 10 + 2;
	}
	else if (key == '3' && gives_input){
		gridSize = gridSize * 10 + 3;
	}
	else if (key == '4' && gives_input){
		gridSize = gridSize * 10 + 4;
	}
	else if (key == '5' && gives_input){
		gridSize = gridSize * 10 + 5;
	}
	else if (key == '6' && gives_input){
		gridSize = gridSize * 10 + 6;
	}
	else if (key == '7' && gives_input){
		gridSize = gridSize * 10 + 7;
	}
	else if (key == '8' && gives_input){
		gridSize = gridSize * 10 + 8;
	}
	else if (key == '9' && gives_input){
		gridSize = gridSize * 10 + 9;
	}
	else if (key == '0' && gives_input){
		gridSize = gridSize * 10;
	}
}

void drawText(const char *text, int length, int x, int y){
	glMatrixMode(GL_PROJECTION);
	double matrix[16];
	glGetDoublev(GL_PROJECTION_MATRIX, matrix);
	glLoadIdentity(); // reset PROJECTION matrix to identity matrix
	glOrtho(0, winWidth, 0, winHeight, -5, 5); // orthographic perspective
	glMatrixMode(GL_MODELVIEW); // change current matrix to MODELVIEW matrix again
	glLoadIdentity(); // reset it to identity matrix
	glPushMatrix(); // push current state of MODELVIEW matrix to stack
	glLoadIdentity(); // reset it again. (may not be required, but it my convention)
	glRasterPos2i(x, y);
	for (int i = 0; i<length; i++){
		glutBitmapCharacter(GLUT_BITMAP_9_BY_15, (int)text[i]);
	}
	glEnd();
	glPopMatrix(); // get MODELVIEW matrix value from stack
	glMatrixMode(GL_PROJECTION); // change current matrix mode to PROJECTION
	glLoadMatrixd(matrix); // reset
	glMatrixMode(GL_MODELVIEW); // change current matrix mode to MODELVIEW
}

void changeSize(int w, int h) {

	// Prevent a divide by zero, when window is too short
	// (you cant make a window of zero width).
	if (h == 0)
		h = 1;
	float ratio = 1.0* w / h;

	// Use the Projection Matrix
	glMatrixMode(GL_PROJECTION);

	// Reset Matrix
	glLoadIdentity();

	// Set the viewport to be the entire window
	glViewport(0, 0, w, h);

	// Set the correct perspective.
	gluPerspective(45, ratio, 1, 1000);

	// Get Back to the Modelview
	glMatrixMode(GL_MODELVIEW);
}