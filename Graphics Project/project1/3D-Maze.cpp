#include <windows.h>
#include <stdio.h>
#include <stdlib.h>
#include <GL/gl.h>
#include <GL/glu.h>
#include <GL/glut.h>
#include <string.h>
#include <math.h>
#include <time.h>  
#include "SOIL.h"
#define T1_ID 0
#define T2_ID 1
#define T3_ID 2
#define BLUE_ID 3
#define RED_ID 4
#define GREEN_ID 5
#define EMPTY_ID 6
#define TELEPORT_ID 7
#define BLOCK_SIZE 1.5
#define DISTANCE (3.7*BLOCK_SIZE) / 4
#define WALL_WIDTH (BLOCK_SIZE - DISTANCE)

#pragma warning (disable : 4996)

//function prototypes
void readMaze(char *name);
void initTextures();
int isHole();
void init();
void drawText(const char *text, int length, int x, int y);
void drawFinalScore();
void rotateCamera(float speed);
void mouseMove(int x, int y);
int checkCollision(float plusx, float plusz);
void processNormalKeys(unsigned char key, int x, int y);
void move(float sign);
void drawBottom(int i, int j, int l);
void drawTop(int i, int j, int l);
void drawSurface(int i, int j, int l, float a1, float b1, float a2, float b2);
void drawRightDoor(int i, int j, int l, float a1, float b1, float a2, float b2);
void drawDownDoor(int i, int j, int l, float a1, float b1, float a2, float b2);
void renderScene(void);
void changeSize(int w, int h);

int winPosX = 200, winPosY = 200, winWidth = 640, winHeight = 480;
float angle = 0.0, alpha = 1.0; //angle for rotation around the maze
float lx = 0.0f, ly = 0, lz = -1.0f;
int K, L, N, current_level, current_position, current_score, number_of_moves, hammers_left; //numeric int
int block_above = 0, hole_forward = 0, rmode = 0, vmode = 0, game_end; //boolean int
int oldMouseX, oldMouseY;
char score_text[20], *block_above_text, *hole_forward_text;
float normalx = 0, normaly = 0, normalz = 0;
float tx1, ty1, tx2, ty2, tx3, ty3, tx4, ty4;

GLuint T1, T2, T3, Red, Blue, Green, Terrain, Teleport, Wall;
GLdouble eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ;

float lookAngle = 360.0;
float dx = 0.0, dy = 1.0, dz = -1.0;

GLfloat  lightDiffuseColour[] = { 1.0f, 1.0f, 1.0f, 1.0f };
GLfloat  lightPosition[] = { -2.0f, 0.0f, 2.0f, 0.0f};
GLfloat  lp[] = {-2 + (N/2)*BLOCK_SIZE, 5, 2 + (N/2)*BLOCK_SIZE, 1.0f };

struct block{
	float bx, bz;
	int id;
};


struct level{
	int id = 0;
	block *map;
	int portal[2];
};

level *levels;


void readMaze(char *name){

	FILE *file = fopen(name, "r");
	if (file == NULL) exit(0);
	char buf[32];
	//reads L
	while (fscanf(file, "%s", buf) != EOF){
		if (strcmp(buf, "=") == 0){
			fscanf(file, "%s", buf);
			L = atoi(buf);
			break;
		}
	}
	//reads N
	while (fscanf(file, "%s", buf) != EOF){
		if (strcmp(buf, "=") == 0){
			fscanf(file, "%s", buf);
			N = atoi(buf);
			break;
		}
	}
	//reads K
	while (fscanf(file, "%s", buf) != EOF){
		if (strcmp(buf, "=") == 0){
			fscanf(file, "%s", buf);
			K = atoi(buf);
			break;
		}
	}
	levels = (level *)malloc(L *sizeof(level));
	float r = 0;
	int empty_found = 0;
	int tid = 0;
	int index = 0, portals_found = 0;
	for (int l = 0; l < L; l++){
		index = 0;
		levels[l].map = (block *)malloc(N * N *sizeof(block));
		fscanf(file, "%s", buf); //word LEVEL
		fscanf(file, "%s", buf); //number of LEVEL
		for (int i = 0; i < N; i++){
			for (int j = 0; j < N; j++){
				fscanf(file, "%s", buf);
				if (strcmp(buf, "T1") == 0) tid = T1_ID;
				else if (strcmp(buf, "T2") == 0) tid = T2_ID;
				else if (strcmp(buf, "T3") == 0) tid = T3_ID;
				else if (strcmp(buf, "B") == 0) tid = BLUE_ID;
				else if (strcmp(buf, "G") == 0) tid = GREEN_ID;
				else if (strcmp(buf, "R") == 0) tid = RED_ID;
				else if (strcmp(buf, "W") == 0) {
					tid = TELEPORT_ID;
					levels[l].portal[portals_found] = index;
					portals_found += 1;
				}	
				else if (strcmp(buf, "E") == 0){
					tid = EMPTY_ID;
					if (l == 0){
						empty_found += 1;
						r = static_cast <float> (rand()) / static_cast <float> (RAND_MAX);
						if (r <= 1.0 / empty_found){
							current_position = index;
							dx = tx1 + i*BLOCK_SIZE + BLOCK_SIZE / 2;
							dz = ty1 + j*BLOCK_SIZE - BLOCK_SIZE / 2;
						}
					}
				}
				levels[l].map[index].id = tid;
				levels[l].map[index].bx = tx1 + i*BLOCK_SIZE;
				levels[l].map[index].bz = ty1 + j*BLOCK_SIZE;
				index += 1;
			}
		}
	}
}

void initTextures(){

	T1 = SOIL_load_OGL_texture
		(
		"T1.png",
		SOIL_LOAD_AUTO,
		SOIL_CREATE_NEW_ID,
		SOIL_FLAG_MIPMAPS | SOIL_FLAG_INVERT_Y | SOIL_FLAG_NTSC_SAFE_RGB | SOIL_FLAG_COMPRESS_TO_DXT
		);
	T2 = SOIL_load_OGL_texture
		(
		"T2.png",
		SOIL_LOAD_AUTO,
		SOIL_CREATE_NEW_ID,
		SOIL_FLAG_MIPMAPS | SOIL_FLAG_INVERT_Y | SOIL_FLAG_NTSC_SAFE_RGB | SOIL_FLAG_COMPRESS_TO_DXT
		);
	T3 = SOIL_load_OGL_texture
		(
		"T3.png",
		SOIL_LOAD_AUTO,
		SOIL_CREATE_NEW_ID,
		SOIL_FLAG_MIPMAPS | SOIL_FLAG_INVERT_Y | SOIL_FLAG_NTSC_SAFE_RGB | SOIL_FLAG_COMPRESS_TO_DXT
		);
	Red = SOIL_load_OGL_texture
		(
		"Red.png",
		SOIL_LOAD_AUTO,
		SOIL_CREATE_NEW_ID,
		SOIL_FLAG_MIPMAPS | SOIL_FLAG_INVERT_Y | SOIL_FLAG_NTSC_SAFE_RGB | SOIL_FLAG_COMPRESS_TO_DXT
		);
	Green = SOIL_load_OGL_texture
		(
		"Green.png",
		SOIL_LOAD_AUTO,
		SOIL_CREATE_NEW_ID,
		SOIL_FLAG_MIPMAPS | SOIL_FLAG_INVERT_Y | SOIL_FLAG_NTSC_SAFE_RGB | SOIL_FLAG_COMPRESS_TO_DXT
		);
	Blue = SOIL_load_OGL_texture
		(
		"Blue.png",
		SOIL_LOAD_AUTO,
		SOIL_CREATE_NEW_ID,
		SOIL_FLAG_MIPMAPS | SOIL_FLAG_INVERT_Y | SOIL_FLAG_NTSC_SAFE_RGB | SOIL_FLAG_COMPRESS_TO_DXT
		);
	Terrain = SOIL_load_OGL_texture
		(
		"Terrain.png",
		SOIL_LOAD_AUTO,
		SOIL_CREATE_NEW_ID,
		SOIL_FLAG_MIPMAPS | SOIL_FLAG_INVERT_Y | SOIL_FLAG_NTSC_SAFE_RGB | SOIL_FLAG_COMPRESS_TO_DXT
		);
	Teleport = SOIL_load_OGL_texture
		(
		"Teleport.png",
		SOIL_LOAD_AUTO,
		SOIL_CREATE_NEW_ID,
		SOIL_FLAG_MIPMAPS | SOIL_FLAG_INVERT_Y | SOIL_FLAG_NTSC_SAFE_RGB | SOIL_FLAG_COMPRESS_TO_DXT
		);
	Wall = SOIL_load_OGL_texture
		(
		"Wall.png",
		SOIL_LOAD_AUTO,
		SOIL_CREATE_NEW_ID,
		SOIL_FLAG_MIPMAPS | SOIL_FLAG_INVERT_Y | SOIL_FLAG_NTSC_SAFE_RGB | SOIL_FLAG_COMPRESS_TO_DXT
		);
}

void init(){

	glEnable(GL_DEPTH_TEST);
	glEnable(GL_COLOR_MATERIAL);
	glFrontFace(GL_CCW);     
	glShadeModel(GL_SMOOTH);
	glLightfv(GL_LIGHT0, GL_DIFFUSE, lightDiffuseColour);
	glLightfv(GL_LIGHT0, GL_POSITION, lp);
	glEnable(GL_LIGHT0);
	glEnable(GL_LIGHTING);
	SetCursorPos((winPosX + winWidth) / 2, (winPosY + winHeight) / 2);
	block_above = 0;
	hole_forward = 0;
	rmode = 0;
	vmode = 0;
	game_end = 0;
	number_of_moves = 0;
	current_level = 0;
	game_end = 0;
	oldMouseX = (winPosX + winWidth)/2;
	oldMouseY = (winPosY + winHeight)/2;
	number_of_moves = 0;
	srand(time(NULL));
	tx1 = -2; ty1 = 2;
	tx2 = tx1 + BLOCK_SIZE; ty2 = ty1;
	tx3 = tx2; ty3 = ty2 - BLOCK_SIZE;
	tx4 = tx2 - BLOCK_SIZE; ty4 = ty3;
	initTextures();
	readMaze("maze.maz");
	hammers_left = K;
	current_score = N*N;
	dy = 1.0;
	eyeX = 1.4; eyeY = 5 + N*BLOCK_SIZE + BLOCK_SIZE*L; eyeZ = 5.0; upY = 1.0;
	centerX = 1.4, centerY = 1.0; centerZ = 4;
}

int isHole(){

	int destroyed = 0;
	int id1 = 0, id2 = 0;
	if (current_level == 0) return 0;
	if (lookAngle > 45 && lookAngle < 135){
		if (current_position + N < N*N){
			id1 = levels[current_level].map[current_position + N].id;
			id2 = levels[current_level - 1].map[current_position + N].id;
			if (id1 == EMPTY_ID && (id2 == EMPTY_ID || id2 == TELEPORT_ID)) return 1;
		}
	}
	else if (lookAngle > 135 && lookAngle < 225){
		if (current_position - 1 >= 0){
			id1 = levels[current_level].map[current_position - 1].id;
			id2 = levels[current_level - 1].map[current_position - 1].id;
			if (id1 == EMPTY_ID && (id2 == EMPTY_ID || id2 == TELEPORT_ID)) return 1;
		}
	}
	else if (lookAngle > 225 && lookAngle < 315){
		if (current_position - N >= 0){
			id1 = levels[current_level].map[current_position - N].id;
			id2 = levels[current_level - 1].map[current_position - N].id;
			if (id1 == EMPTY_ID && (id2 == EMPTY_ID || id2 == TELEPORT_ID)) return 1;
		}
	}
	else if (lookAngle < 45 || (lookAngle > 315 && lookAngle <= 360)){
		id1 = levels[current_level].map[current_position + 1].id;
		id2 = levels[current_level - 1].map[current_position + 1].id;
		if (id1 == EMPTY_ID && (id2 == EMPTY_ID || id2 == TELEPORT_ID)) return 1;
	}
	return 0;
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

void drawFinalScore(){

	glMatrixMode(GL_PROJECTION);
	glPushMatrix();
	glLoadIdentity();
	glOrtho(0.0, winWidth,winHeight, 0.0, -5, 5);
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glBegin(GL_QUADS);
	glColor4f(0.5f, 1.0f, 1.0, 0.5);
	glVertex2f(winWidth / 2 - winWidth / 3, winHeight / 2 + winHeight / 10);
	glVertex2f(winWidth / 2 + winWidth / 3, winHeight / 2 + winHeight / 10);
	glVertex2f(winWidth / 2 + winWidth / 3, winHeight / 2 - winHeight / 10);
	glVertex2f(winWidth / 2 - winWidth / 3, winHeight / 2 - winHeight / 10);
	glEnd();
	glMatrixMode(GL_PROJECTION);
	glPopMatrix();
	glMatrixMode(GL_MODELVIEW);
}


void rotateCamera(float speed){

	lookAngle += speed;
	if (speed < 0){
		if (lookAngle <= 0) lookAngle = 360.0 + lookAngle;
	}
	if (speed > 0){
		if (lookAngle> 360) lookAngle = speed;
	}
}

void mouseMove(int x, int y){
	if (game_end) return;
	glutSetCursor(GLUT_CURSOR_NONE);
	POINT mouse;
	GetCursorPos(&mouse);	// Get the 2D mouse cursor (x,y) position	
	int screen_pos_x = glutGet((GLenum)GLUT_WINDOW_X);
	int screen_pos_y = glutGet((GLenum)GLUT_WINDOW_Y);

	if (mouse.x - oldMouseX < 0) {
		rotateCamera(2);
	}
	else {
		rotateCamera(-2);
	}
	oldMouseX = mouse.x;
	oldMouseY = mouse.y;

	if (mouse.x <= screen_pos_x + 10 || mouse.x >= screen_pos_x + winWidth - 10) SetCursorPos((screen_pos_x + winWidth) / 2, (screen_pos_y + winHeight) / 2);
}



int checkCollision(float plusx, float plusz){
	
	int collisionFound = 0;
	float tempx, tempz;

	if (plusz < 0){
	
		if (current_position % N - 1 < 0) {

			tempz = levels[current_level].map[current_position].bz;
			if (dz + plusz <= tempz - BLOCK_SIZE + 0.14)
			return 1;
		}
		else{
			tempz = levels[current_level].map[current_position - 1].bz;
			if (dz + plusz <= tempz + 0.14){
				if (levels[current_level].map[current_position - 1].id == EMPTY_ID){

					while (current_level > 0){
						if (levels[current_level - 1].map[current_position - 1].id == EMPTY_ID) {
							current_level -= 1;
							dy -= BLOCK_SIZE;
							
						}
						else break;
					}
					current_position -= 1;
					current_score -= 10;
					return 0;
				}
				else if (levels[current_level].map[current_position - 1].id == TELEPORT_ID){
					if (levels[current_level].portal[0] == current_position - 1){
						current_position = levels[current_level].portal[1];
						dx = levels[current_level].map[current_position].bx + BLOCK_SIZE / 2;
						dz = levels[current_level].map[current_position].bz - BLOCK_SIZE / 2;
					}
					else{
						current_position = levels[current_level].portal[0];
						dx = levels[current_level].map[current_position].bx + BLOCK_SIZE / 2;
						dz = levels[current_level].map[current_position].bz - BLOCK_SIZE / 2;
					}
					current_score -= 10;
					return 0;
				}
				else {
					collisionFound = 1;
					return 1;
				}
			}
		}
	}
	if (plusz > 0){
	
		if (current_position %N + 1 == N) {
			tempz = levels[current_level].map[current_position].bz;
			if (dz + plusz >= tempz - 0.14)
				return 1;
		}
		else{
			tempz = levels[current_level].map[current_position + 1].bz - BLOCK_SIZE;
			if (dz + plusz >= tempz - 0.14){
				if (levels[current_level].map[current_position + 1].id == EMPTY_ID){
					while (current_level > 0){
						if (levels[current_level - 1].map[current_position + 1].id == EMPTY_ID) {
							current_level -= 1;
							dy -= BLOCK_SIZE;
						}
						else break;
					}
					current_position += 1;
					current_score -= 10;
					return 0;
				}
				else if (levels[current_level].map[current_position + 1].id == TELEPORT_ID){
					if (levels[current_level].portal[0] == current_position + 1){
						current_position = levels[current_level].portal[1];
						dx = levels[current_level].map[current_position].bx + BLOCK_SIZE / 2;
						dz = levels[current_level].map[current_position].bz - BLOCK_SIZE / 2;
					}
					else{
						current_position = levels[current_level].portal[0];
						dx = levels[current_level].map[current_position].bx + BLOCK_SIZE / 2;
						dz = levels[current_level].map[current_position].bz - BLOCK_SIZE / 2;
					}
					current_score -= 10;
					return 0;
				}
				else {
					collisionFound = 1;
					return 1;
				}
			}
		}
	}
	if (plusx < 0){
		if (current_position - N < 0) {
			
			tempx = levels[current_level].map[current_position].bx + BLOCK_SIZE;
			if (dx + plusx <= tempx - BLOCK_SIZE + 0.14)
			return 1;
		}
		else{
			tempx = levels[current_level].map[current_position - N].bx + BLOCK_SIZE;
			if (dx + plusx <= tempx + 0.14){

				if (levels[current_level].map[current_position - N].id == EMPTY_ID){

					while (current_level > 0){
						if (levels[current_level - 1].map[current_position - N].id == EMPTY_ID) {
							current_level -= 1;
							dy -= BLOCK_SIZE;
						}
						else break;
					}
					current_position -= N;
					current_score -= 10;
					return 0;
				}
				else if (levels[current_level].map[current_position - N].id == TELEPORT_ID){
					if (levels[current_level].portal[0] == current_position - N){
						current_position = levels[current_level].portal[1];
						dx = levels[current_level].map[current_position].bx + BLOCK_SIZE / 2;
						dz = levels[current_level].map[current_position].bz - BLOCK_SIZE / 2;
					}
					else{
						current_position = levels[current_level].portal[0];
						dx = levels[current_level].map[current_position].bx + BLOCK_SIZE / 2;
						dz = levels[current_level].map[current_position].bz - BLOCK_SIZE / 2;
					}
					current_score -= 10;
					return 0;

				}
				else {
					collisionFound = 1;
					return 1;
				}
			}
		}
	}
	if (plusx > 0){
		
		if (current_position + N >= N*N) {
			tempx = levels[current_level].map[current_position].bx;
			if (dx + plusx >= tempx + BLOCK_SIZE - 0.14) return 1;
		}
		else{
			tempx = levels[current_level].map[current_position + N].bx;
			if (dx + plusx >= tempx - 0.14){

				if (levels[current_level].map[current_position + N].id == EMPTY_ID){
					while (current_level > 0){
						if (levels[current_level - 1].map[current_position + N].id == EMPTY_ID) {
							current_level -= 1;
							dy -= BLOCK_SIZE;
						}
						else break;
					}
					current_position += N;
					current_score -= 10;
					return 0;
				}
				else if (levels[current_level].map[current_position + N].id == TELEPORT_ID){
					if (levels[current_level].portal[0] == current_position + N){
						current_position = levels[current_level].portal[1];
						dx = levels[current_level].map[current_position].bx + BLOCK_SIZE / 2;
						dz = levels[current_level].map[current_position + N].bz - BLOCK_SIZE / 2;
					}
					else{
						current_position = levels[current_level].portal[0];
						dx = levels[current_level].map[current_position].bx + BLOCK_SIZE / 2;
						dz = levels[current_level].map[current_position].bz - BLOCK_SIZE / 2;
					}
					current_score -= 10;
					return 0;
				}
				else {
					collisionFound = 1;
					return 1;
				}
			}
		}
	}
	return 0;
}

void processNormalKeys(unsigned char key, int x, int y) {

	float fraction = 0.1f;

	if (key == 27)
		exit(0);
	else if (key == 'r'){

		if (!game_end) rmode = 1;
		else init();
	}
	else if (game_end) return;
	else if (key == 32){
		if (current_level + 1 < L){

			if (levels[current_level + 1].map[current_position].id == EMPTY_ID){
				current_level += 1;
				dy += BLOCK_SIZE;
			}
		}
		else game_end = 1;
	}
	else if (key == 'x') {
		if (current_level != L - 1) current_score = 0;
		game_end = 1;
	}
	else if (key == 'e' && current_level == L-1) game_end = 1;
	else if (key == 'o'){
		if (alpha > 0.1) alpha -= 0.1;
	}
	else if (key == 'p'){
		if (alpha < 1.0) alpha += 0.1;
	}
	else if (key == 'h'){
		if (hammers_left > 0){

			int destroyed = 0;
			int id = 0;
			if (lookAngle > 45 && lookAngle < 135){
				if (current_position + N < N*N){
					id =  levels[current_level].map[current_position + N].id;
					if (id != EMPTY_ID && id != TELEPORT_ID){
						levels[current_level].map[current_position + N].id = EMPTY_ID;
						destroyed = 1;
					}
					
				}
			}

			else if (lookAngle > 135 && lookAngle < 225){
				if (current_position - 1 >= 0){
					id = levels[current_level].map[current_position -1].id;
					if (id != EMPTY_ID && id != TELEPORT_ID){
						levels[current_level].map[current_position - 1].id = EMPTY_ID;
						destroyed = 1;
					}
				}
			}
			else if (lookAngle > 225 && lookAngle < 315){
				if (current_position - N >= 0){
					id = levels[current_level].map[current_position - N].id;
					if (id != EMPTY_ID && id != TELEPORT_ID){
						levels[current_level].map[current_position - N].id = EMPTY_ID;
						destroyed = 1;
					}
				}
			}
			else if (lookAngle < 45 || (lookAngle > 315 && lookAngle <= 360)){
				id = levels[current_level].map[current_position + 1].id;
				if (id != EMPTY_ID && id != TELEPORT_ID){
					levels[current_level].map[current_position + 1].id = EMPTY_ID;
					destroyed = 1;
				}
			}



			if (destroyed){
				current_score -= 50;
				hammers_left -= 1;
			}
			
		}
	}
	else if (key == 'g'){
		if (current_level != L - 1){
			if (levels[current_level + 1].map[current_position].id != EMPTY_ID && levels[current_level + 1].map[current_position].id != TELEPORT_ID){
				levels[current_level + 1].map[current_position].id = EMPTY_ID;
				current_score -= 50;
				hammers_left -= 1;
			}
		}
	}
	else if (key == 'w'){
		move(1.0);
	}
	else if (key == 's'){
		move(-1.0);
		
	}
	else if (key == 'v'){
		rmode = 0;
		vmode = !vmode;
	}
}

void move(float sign){

	float trueAngle = 0;
	float a, b, c;
	float mapx = (tx1 + N*BLOCK_SIZE) / 2;
	float mapy = (ty1 - N*BLOCK_SIZE) / 2;
	float plusx = 0, plusz = 0, tx = 0, tz = 0;
	if (lookAngle > 90 && lookAngle < 180){

		trueAngle = lookAngle - 90;
		tx = 1.0*sign;
		tz = -1.0*sign;
	}
	else if (lookAngle > 180 && lookAngle < 270){

		trueAngle = 270 - lookAngle;
		tx = -1.0*sign;
		tz = -1.0*sign;
	}
	else if (lookAngle > 270 && lookAngle < 360){

		trueAngle = lookAngle - 270;
		tx = -1.0*sign;
		tz = 1.0*sign;
	}
	else if (lookAngle < 90){

		trueAngle = 90 - lookAngle;
		tx = 1.0*sign;
		tz = 1.0*sign;
	}
	else if (lookAngle == 90){
		plusx += 0.14*sign;
	}
	else if (lookAngle == 180){
		plusz -= 0.14*sign;
	}
	else if (lookAngle == 270){
		plusx -= 0.14*sign;
	}

	else if (lookAngle == 360){
		plusz += 0.14*sign;
	}

	if (tx != 0){
		c = sqrt((dx - mapx)*(dx - mapx) + (dz - mapy)*(dz - mapy));
		c += 0.005 * sign;
		a = cos(trueAngle*3.14 / 180)*c;
		b = sin(trueAngle*3.14 / 180)*c;
		plusx = 0.03*a*tx;
		plusz = 0.03* b*tz;
	}

	if (!checkCollision(plusx, plusz)){

		dx += plusx;
		dz += plusz;

	}

}

void drawBottom(int i, int j, int l){
	glColor4f(1.0f, 1.0f, 1.0f, alpha);
	glEnable(GL_BLEND);
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	glBegin(GL_QUADS);
	glNormal3f(normalx, normaly, normalz);
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

void drawTop(int i, int j, int l){
	glColor4f(1.0f, 1.0f, 1.0f, alpha);
	glEnable(GL_BLEND);
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	glBegin(GL_QUADS);
	glNormal3f(normalx, normaly, normalz);
	glTexCoord2f(0.0f, 0.0f);
	glVertex3f(tx1 + i*BLOCK_SIZE, BLOCK_SIZE * l + BLOCK_SIZE, ty1 + j*BLOCK_SIZE);
	glTexCoord2f(1.0f, 0.0f);
	glVertex3f(tx2 + i*BLOCK_SIZE, BLOCK_SIZE * l + BLOCK_SIZE, ty2 + j*BLOCK_SIZE);
	glTexCoord2f(1.0f, 1.0f);
	glVertex3f(tx3 + i*BLOCK_SIZE, BLOCK_SIZE * l + BLOCK_SIZE, ty3 + j*BLOCK_SIZE);
	glTexCoord2f(0.0f, 1.0f);
	glVertex3f(tx4 + i*BLOCK_SIZE, BLOCK_SIZE * l + BLOCK_SIZE, ty4 + j*BLOCK_SIZE);
	glEnd();
	glDisable(GL_BLEND);
}


//down (1,2), up (3,4), right (3,2),left (4,1)
void drawSurface(int i, int j, int l, float a1, float b1, float a2, float b2){
	glColor4f(1.0f, 1.0f, 1.0f, alpha);
	glEnable(GL_BLEND);
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	glBegin(GL_QUADS);
	glNormal3f(normalx, normaly, normalz);
	glTexCoord2f(0.0f, 0.0f);
	glVertex3f(a1 + i*BLOCK_SIZE, BLOCK_SIZE * l + BLOCK_SIZE, b1 + j*BLOCK_SIZE);
	glTexCoord2f(1.0f, 0.0f);
	glVertex3f(a2 + i*BLOCK_SIZE, BLOCK_SIZE * l + BLOCK_SIZE, b2 + j*BLOCK_SIZE);
	glTexCoord2f(1.0f, 1.0f);
	glVertex3f(a2 + i*BLOCK_SIZE, BLOCK_SIZE * l, b2 + j*BLOCK_SIZE);
	glTexCoord2f(0.0f, 1.0f);
	glVertex3f(a1 + i*BLOCK_SIZE, BLOCK_SIZE * l, b1 + j*BLOCK_SIZE);
	glEnd();
	glDisable(GL_BLEND);
}

void drawRightDoor(int i, int j, int l, float a1, float b1, float a2, float b2){

	//first part
	//right surface
	glBegin(GL_QUADS);
	glNormal3f(-1.0, 0.0, 0.0);
	glTexCoord2f(0.0f, 0.0f);
	glVertex3f(a1 + i*BLOCK_SIZE, BLOCK_SIZE * l + BLOCK_SIZE, b1 + j*BLOCK_SIZE + WALL_WIDTH);
	glTexCoord2f(1.0f, 0.0f);
	glVertex3f(a2 + i*BLOCK_SIZE, BLOCK_SIZE * l + BLOCK_SIZE, b2 + j*BLOCK_SIZE - WALL_WIDTH);
	glTexCoord2f(1.0f, 1.0f);
	glVertex3f(a2 + i*BLOCK_SIZE, BLOCK_SIZE * l + DISTANCE, b2 + j*BLOCK_SIZE - WALL_WIDTH);
	glTexCoord2f(0.0f, 1.0f);
	glVertex3f(a1 + i*BLOCK_SIZE, BLOCK_SIZE * l + DISTANCE, b1 + j*BLOCK_SIZE + WALL_WIDTH);
	glEnd();
	//left surface
	glBegin(GL_QUADS);
	glNormal3f(1.0, 0.0, 0.0);
	glTexCoord2f(0.0f, 0.0f);
	glVertex3f(a1 + i*BLOCK_SIZE-0.01, BLOCK_SIZE * l + BLOCK_SIZE, b1 + j*BLOCK_SIZE + WALL_WIDTH);
	glTexCoord2f(1.0f, 0.0f);
	glVertex3f(a2 + i*BLOCK_SIZE-0.01, BLOCK_SIZE * l + BLOCK_SIZE, b2 + j*BLOCK_SIZE - WALL_WIDTH);
	glTexCoord2f(1.0f, 1.0f);
	glVertex3f(a2 + i*BLOCK_SIZE-0.01, BLOCK_SIZE * l + DISTANCE, b2 + j*BLOCK_SIZE - WALL_WIDTH);
	glTexCoord2f(0.0f, 1.0f);
	glVertex3f(a1 + i*BLOCK_SIZE-0.01, BLOCK_SIZE * l + DISTANCE, b1 + j*BLOCK_SIZE + WALL_WIDTH);
	glEnd();
	
	//left side
	//right surface
	glBegin(GL_QUADS);
	glNormal3f(-1.0, 0.0, 0.0);
	glTexCoord2f(0.0f, 0.0f);
	glVertex3f(a1 + i*BLOCK_SIZE, BLOCK_SIZE * l + BLOCK_SIZE, b1 + j*BLOCK_SIZE);
	glTexCoord2f(1.0f, 0.0f);
	glVertex3f(a2 + i*BLOCK_SIZE, BLOCK_SIZE * l + BLOCK_SIZE, b2 + j*BLOCK_SIZE - DISTANCE);
	glTexCoord2f(1.0f, 1.0f);
	glVertex3f(a2 + i*BLOCK_SIZE, BLOCK_SIZE * l, b2 + j*BLOCK_SIZE - DISTANCE);
	glTexCoord2f(0.0f, 1.0f);
	glVertex3f(a1 + i*BLOCK_SIZE, BLOCK_SIZE * l, b1 + j*BLOCK_SIZE);
	glEnd();
	//left surface
	glBegin(GL_QUADS);
	glNormal3f(1.0, 0.0, 0.0);
	glTexCoord2f(0.0f, 0.0f);
	glVertex3f(a1 + i*BLOCK_SIZE-0.01, BLOCK_SIZE * l + BLOCK_SIZE, b1 + j*BLOCK_SIZE);
	glTexCoord2f(1.0f, 0.0f);
	glVertex3f(a2 + i*BLOCK_SIZE-0.01, BLOCK_SIZE * l + BLOCK_SIZE, b2 + j*BLOCK_SIZE - DISTANCE);
	glTexCoord2f(1.0f, 1.0f);
	glVertex3f(a2 + i*BLOCK_SIZE-0.01, BLOCK_SIZE * l, b2 + j*BLOCK_SIZE - DISTANCE);
	glTexCoord2f(0.0f, 1.0f);
	glVertex3f(a1 + i*BLOCK_SIZE-0.01, BLOCK_SIZE * l, b1 + j*BLOCK_SIZE);
	glEnd();

	//right side
	//right surface
	glBegin(GL_QUADS);
	glNormal3f(-1.0, 0.0, 0.0);
	glTexCoord2f(0.0f, 0.0f);
	glVertex3f(a1 + i*BLOCK_SIZE, BLOCK_SIZE * l + BLOCK_SIZE, b1 + j*BLOCK_SIZE + DISTANCE);
	glTexCoord2f(1.0f, 0.0f);
	glVertex3f(a2 + i*BLOCK_SIZE, BLOCK_SIZE * l + BLOCK_SIZE, b2 + j*BLOCK_SIZE);
	glTexCoord2f(1.0f, 1.0f);
	glVertex3f(a2 + i*BLOCK_SIZE, BLOCK_SIZE * l, b2 + j*BLOCK_SIZE);
	glTexCoord2f(0.0f, 1.0f);
	glVertex3f(a1 + i*BLOCK_SIZE, BLOCK_SIZE * l, b1 + j*BLOCK_SIZE + DISTANCE);
	glEnd();
	//left surface
	glBegin(GL_QUADS);
	glNormal3f(1.0, 0.0, 0.0);
	glTexCoord2f(0.0f, 0.0f);
	glVertex3f(a1 + i*BLOCK_SIZE- 0.01, BLOCK_SIZE * l + BLOCK_SIZE, b1 + j*BLOCK_SIZE + DISTANCE);
	glTexCoord2f(1.0f, 0.0f);
	glVertex3f(a2 + i*BLOCK_SIZE - 0.01, BLOCK_SIZE * l + BLOCK_SIZE, b2 + j*BLOCK_SIZE);
	glTexCoord2f(1.0f, 1.0f);
	glVertex3f(a2 + i*BLOCK_SIZE - 0.01, BLOCK_SIZE * l, b2 + j*BLOCK_SIZE);
	glTexCoord2f(0.0f, 1.0f);
	glVertex3f(a1 + i*BLOCK_SIZE - 0.01, BLOCK_SIZE * l, b1 + j*BLOCK_SIZE + DISTANCE);
	glEnd();
}

void drawDownDoor(int i, int j, int l, float a1, float b1, float a2, float b2){
	//first part 
	//front surface
	glNormal3f(0.0, 0.0, -1.0);
	glBegin(GL_QUADS);
	glTexCoord2f(0.0f, 0.0f);
	glVertex3f(a1 + i*BLOCK_SIZE + WALL_WIDTH, BLOCK_SIZE * l + BLOCK_SIZE, b1 + j*BLOCK_SIZE);
	glTexCoord2f(1.0f, 0.0f);
	glVertex3f(a2 + i*BLOCK_SIZE - WALL_WIDTH, BLOCK_SIZE * l + BLOCK_SIZE, b2 + j*BLOCK_SIZE);
	glTexCoord2f(1.0f, 1.0f);
	glVertex3f(a2 + i*BLOCK_SIZE - WALL_WIDTH, BLOCK_SIZE * l + DISTANCE, b2 + j*BLOCK_SIZE);
	glTexCoord2f(0.0f, 1.0f);
	glVertex3f(a1 + i*BLOCK_SIZE + WALL_WIDTH, BLOCK_SIZE * l + DISTANCE, b1 + j*BLOCK_SIZE);
	glEnd();
	//back surface
	glNormal3f(0.0, 0.0, 1.0);
	glBegin(GL_QUADS);
	glTexCoord2f(0.0f, 0.0f);
	glVertex3f(a1 + i*BLOCK_SIZE + WALL_WIDTH, BLOCK_SIZE * l + BLOCK_SIZE, b1 + j*BLOCK_SIZE-0.01);
	glTexCoord2f(1.0f, 0.0f);
	glVertex3f(a2 + i*BLOCK_SIZE - WALL_WIDTH, BLOCK_SIZE * l + BLOCK_SIZE, b2 + j*BLOCK_SIZE-0.01);
	glTexCoord2f(1.0f, 1.0f);
	glVertex3f(a2 + i*BLOCK_SIZE - WALL_WIDTH, BLOCK_SIZE * l + DISTANCE, b2 + j*BLOCK_SIZE-0.01);
	glTexCoord2f(0.0f, 1.0f);
	glVertex3f(a1 + i*BLOCK_SIZE + WALL_WIDTH, BLOCK_SIZE * l + DISTANCE, b1 + j*BLOCK_SIZE-0.01);
	glEnd();

	//back surface
	glNormal3f(0.0, 0.0, 1);
	glBegin(GL_QUADS);
	glTexCoord2f(0.0f, 0.0f);
	glVertex3f(a1 + i*BLOCK_SIZE + WALL_WIDTH, BLOCK_SIZE * l + BLOCK_SIZE, b1 + j*BLOCK_SIZE-0.01);
	glTexCoord2f(1.0f, 0.0f);
	glVertex3f(a2 + i*BLOCK_SIZE - WALL_WIDTH, BLOCK_SIZE * l + BLOCK_SIZE, b2 + j*BLOCK_SIZE - 0.01);
	glTexCoord2f(1.0f, 1.0f);
	glVertex3f(a2 + i*BLOCK_SIZE - WALL_WIDTH, BLOCK_SIZE * l + DISTANCE, b2 + j*BLOCK_SIZE - 0.01);
	glTexCoord2f(0.0f, 1.0f);
	glVertex3f(a1 + i*BLOCK_SIZE + WALL_WIDTH, BLOCK_SIZE * l + DISTANCE, b1 + j*BLOCK_SIZE - 0.01);
	glEnd();

	//left side
	//front surface
	glBegin(GL_QUADS);
	glNormal3f(0.0, 0.0, -1);
	glTexCoord2f(0.0f, 0.0f);
	glVertex3f(a1 + i*BLOCK_SIZE, BLOCK_SIZE * l + BLOCK_SIZE, b1 + j*BLOCK_SIZE);
	glTexCoord2f(1.0f, 0.0f);
	glVertex3f(a2 + i*BLOCK_SIZE - DISTANCE, BLOCK_SIZE * l + BLOCK_SIZE, b2 + j*BLOCK_SIZE);
	glTexCoord2f(1.0f, 1.0f);
	glVertex3f(a2 + i*BLOCK_SIZE - DISTANCE, BLOCK_SIZE * l, b2 + j*BLOCK_SIZE);
	glTexCoord2f(0.0f, 1.0f);
	glVertex3f(a1 + i*BLOCK_SIZE, BLOCK_SIZE *l , b1 + j*BLOCK_SIZE);
	glEnd();
	//back surface
	glBegin(GL_QUADS);
	glNormal3f(0.0, 0.0, 1);
	glTexCoord2f(0.0f, 0.0f);
	glVertex3f(a1 + i*BLOCK_SIZE, BLOCK_SIZE * l + BLOCK_SIZE, b1 + j*BLOCK_SIZE-0.01);
	glTexCoord2f(1.0f, 0.0f);
	glVertex3f(a2 + i*BLOCK_SIZE - DISTANCE, BLOCK_SIZE * l + BLOCK_SIZE, b2 + j*BLOCK_SIZE - 0.01);
	glTexCoord2f(1.0f, 1.0f);
	glVertex3f(a2 + i*BLOCK_SIZE - DISTANCE, BLOCK_SIZE * l, b2 + j*BLOCK_SIZE - 0.01);
	glTexCoord2f(0.0f, 1.0f);
	glVertex3f(a1 + i*BLOCK_SIZE, BLOCK_SIZE *l, b1 + j*BLOCK_SIZE - 0.01);
	glEnd();

	//right side
	//front surface
	glBegin(GL_QUADS);
	glNormal3f(0.0, 0.0, -1);
	glTexCoord2f(0.0f, 0.0f);
	glVertex3f(a1 + i*BLOCK_SIZE + DISTANCE, BLOCK_SIZE * l + BLOCK_SIZE, b1 + j*BLOCK_SIZE);
	glTexCoord2f(1.0f, 0.0f);
	glVertex3f(a2 + i*BLOCK_SIZE, BLOCK_SIZE * l + BLOCK_SIZE, b2 + j*BLOCK_SIZE);
	glTexCoord2f(1.0f, 1.0f);
	glVertex3f(a2 + i*BLOCK_SIZE, BLOCK_SIZE *l, b2 + j*BLOCK_SIZE);
	glTexCoord2f(0.0f, 1.0f);
	glVertex3f(a1 + i*BLOCK_SIZE + DISTANCE, BLOCK_SIZE * l, b1 + j*BLOCK_SIZE);
	glEnd();
	//back surface
	glBegin(GL_QUADS);
	glNormal3f(0.0, 0.0, 1);
	glTexCoord2f(0.0f, 0.0f);
	glVertex3f(a1 + i*BLOCK_SIZE + DISTANCE, BLOCK_SIZE * l + BLOCK_SIZE, b1 + j*BLOCK_SIZE - 0.01);
	glTexCoord2f(1.0f, 0.0f);
	glVertex3f(a2 + i*BLOCK_SIZE, BLOCK_SIZE * l + BLOCK_SIZE, b2 + j*BLOCK_SIZE - 0.01);
	glTexCoord2f(1.0f, 1.0f);
	glVertex3f(a2 + i*BLOCK_SIZE, BLOCK_SIZE *l, b2 + j*BLOCK_SIZE - 0.01);
	glTexCoord2f(0.0f, 1.0f);
	glVertex3f(a1 + i*BLOCK_SIZE + DISTANCE, BLOCK_SIZE * l, b1 + j*BLOCK_SIZE - 0.01);
	glEnd();
}

void renderScene(void) {

	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	//Reset transformations
	glLoadIdentity();

	if (rmode){ //camera outside the maze
		angle -= 0.001 * L;
		eyeX = centerX + 20 * cos(angle);
		eyeY = 4 + L * 3;
		eyeZ = centerZ + 20 * sin(angle);
	}
	
	if (vmode) { //camera inside
		glRotatef(180 - lookAngle, 0.0, 1.0f, 0.0);
		gluLookAt(dx, dy, dz,
			dx + lx, dy+ly, dz + lz ,
			upX, upY, upZ);
	}
	else{
		gluLookAt(eyeX, eyeY, eyeZ,
			centerX, centerY, centerZ,
			0.0f, 1.0f, 0.0f);
	}
	glEnable(GL_TEXTURE_2D);
	int index = 0;
	int isEmpty = 0;
	for (int l = 0; l < L; l++){
		index = 0;
		for (int i = 0; i < N; i++){
			for (int j = 0; j < N; j++){
				isEmpty = 0;
				if (levels[l].map[index].id == BLUE_ID){
					glBindTexture(GL_TEXTURE_2D, Blue);
				}
				else if (levels[l].map[index].id == RED_ID){
					glBindTexture(GL_TEXTURE_2D, Red);
				}
				else if (levels[l].map[index].id == GREEN_ID){
					glBindTexture(GL_TEXTURE_2D, Green);
				}
				else if (levels[l].map[index].id == T1_ID){

					glBindTexture(GL_TEXTURE_2D, T1);
			
				}
				else if (levels[l].map[index].id == T2_ID){

					glBindTexture(GL_TEXTURE_2D, T2);
				
				}
				else if (levels[l].map[index].id == T3_ID){

					glBindTexture(GL_TEXTURE_2D, T3);
				
				}
				else if (levels[l].map[index].id == TELEPORT_ID){

					isEmpty = 1;
					glBindTexture(GL_TEXTURE_2D, Teleport);
					glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
					normalx = 0.0;
					normaly = 1.0;
					normalz = 0.0;
					drawBottom(i, j, l);
				}
				else if (levels[l].map[index].id == EMPTY_ID){

					int id;
					isEmpty = 1;
					glBindTexture(GL_TEXTURE_2D, Terrain);

					if (l == 0 || (l <= L - 1 && (levels[l-1].map[index].id != EMPTY_ID))){
						
						glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
						normalx = 0.0;
						normaly = 1.0;
						normalz = 0.0;
						drawBottom(i, j, l);
					}
					glBindTexture(GL_TEXTURE_2D, Wall);
					if (index % N != 0){
						id = levels[l].map[index - 1].id;
						if (id != EMPTY_ID && id != TELEPORT_ID){
							normalx = 0.0;
							normaly = 0.0;
							normalz = 1.0;
							drawSurface(i, j, l, tx3, ty3, tx4, ty4); //up
						}
					}
					if (index % N != N - 1){
						id = levels[l].map[index + 1].id;
						if (id != EMPTY_ID && id != TELEPORT_ID){
							normalx = 0.0;
							normaly = 0.0;
							normalz = -1.0;
							drawSurface(i, j, l, tx1, ty1, tx2, ty2); //down
						}
						else drawDownDoor(i, j, l, tx1, ty1, tx2, ty2);
					}
					if (index - N >= 0){
						id = levels[l].map[index - N].id;
						if (id != EMPTY_ID && id != TELEPORT_ID){
							normalx = -1.0;
							normaly = 0.0;
							normalz = 0.0;
							drawSurface(i, j, l, tx4, ty4, tx1, ty1); //left
						}
					}
					if (index + N < N*N){
						id = levels[l].map[index + N].id;
						if (id != EMPTY_ID && id != TELEPORT_ID){
							normalx = -1.0;
							normaly = 0.0;
							normalz = 0.0;
							drawSurface(i, j, l, tx3, ty3, tx2, ty2); //right
						}
						else drawRightDoor(i, j, l, tx3, ty3, tx2, ty2);
					}
				}

				if (!isEmpty){
					glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
					int id;
					normalx = 0.0;
					normaly = 1.0;
					normalz = 0.0;
					drawBottom(i, j, l);
					if (l == L - 1){
						normalx = 0.0;
						normaly = 1.0;
						normalz = 0.0;
						drawTop(i, j, l);
					}	
					
					if (index % N != 0){
						id = levels[l].map[index - 1].id;
						if (id != EMPTY_ID){
							normalx = 0.0;
							normaly = 0.0;
							normalz = 1.0;
							drawSurface(i, j, l, tx3, ty3, tx4, ty4);; //up
						}
					}
					else {
						normalx = 0.0;
						normaly = 0.0;
						normalz = 1.0;
						drawSurface(i, j, l, tx3, ty3, tx4, ty4); //up
					}
						
					if (index % N != N - 1){
						id = levels[l].map[index + 1].id;
						if (id != EMPTY_ID){
							normalx = 0.0;
							normaly = 0.0;
							normalz = -1.0;
							drawSurface(i, j, l, tx1, ty1, tx2, ty2); //down
						}
					}
					else {
						normalx = 0.0;
						normaly = 0.0;
						normalz = -1.0;
						drawSurface(i, j, l, tx1, ty1, tx2, ty2); //down
					}
						
					if (index - N >= 0){
						id = levels[l].map[index - N].id;
						if (id != EMPTY_ID){
							normalx = 1.0;
							normaly = 0.0;
							normalz = 0.0;
							drawSurface(i, j, l, tx4, ty4, tx1, ty1); //left
						}
					}
					else {
						normalx = 1.0;
						normaly = 0.0;
						normalz = 0.0;
						drawSurface(i, j, l, tx4, ty4, tx1, ty1); //left
					}
							
					if (index + N < N*N){
						id = levels[l].map[index + N].id;
						if (id != EMPTY_ID){
							normalx = -1.0;
							normaly = 0.0;
							normalz = 0.0;
							drawSurface(i, j, l, tx3, ty3, tx2, ty2); //right
						}
					}
					else{
						normalx = -1.0;
						normaly = 0.0;
						normalz = 0.0;
						drawSurface(i, j, l, tx3, ty3, tx2, ty2);  //right
					}
				}

				lp[0] = -2;
				lp[1] = 2 * L;
				lp[2] = 2 + (N / 2)*BLOCK_SIZE;
				glLightfv(GL_LIGHT0, GL_POSITION, lp);

				if (index == current_position && l == current_level){

					//draw player
					glPushMatrix();
					glColor3f(1.0, 0.0, 0.0);
					glTranslatef(dx, dy - 0.6, dz);
					glColor3f(1.0, 0.2, 0.8);
					glRotatef(lookAngle, 0.0f, 1.0f, 0.0f);
					GLUquadricObj *quadratic;
					quadratic = gluNewQuadric();
					gluCylinder(quadratic, 0.08f, 0.08f, 0.3f, 50, 50);
					glPopMatrix();
					glColor3f(1.0, 1.0, 1.0);
				}
				index += 1;
			}
		}
	}
	glDisable(GL_TEXTURE_2D);

	if (game_end){
		vmode = 0;
		rmode = 0;
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		drawFinalScore();
		glDisable(GL_BLEND);
		glColor3f(0.0, 0.0, 0.0);
		char s[40] = "";
		sprintf(score_text, "%d", current_score);
		strcat(s, "Final Score: ");
		strcat(s, score_text);
		glDisable(GL_DEPTH_TEST);
		drawText(s, strlen(s), winWidth/2-winWidth/10, winHeight/2);
		drawText("Press R to restart game or ESC to exit game", strlen("Press R to restart game or ESC to exit game"), winWidth / 2 - winWidth / 3.4, winHeight / 2 - 20);
		glEnable(GL_DEPTH_TEST);
	}
	else{
		char s[40] = "";
		sprintf(score_text, "%d", current_score);
		strcat(s, "Current Score: ");
		strcat(s, score_text);
		drawText(s, strlen(s), (2.5 / 4)*winWidth, winHeight - 15);
		if (current_level == L - 1) block_above_text = "Block Above: False";
		else {
			if (levels[current_level + 1].map[current_position].id != EMPTY_ID) block_above_text = "Block Above: True";
			else block_above_text = "Block Above: False";
		}
		drawText(block_above_text, strlen(block_above_text), 5, winHeight - 15);

		if (isHole()) drawText("Hole Forward: True", strlen("Hole Forward: True"), 5, winHeight - 35);
		else drawText("Hole Forward: False", strlen("Hole Forward: False"), 5, winHeight - 35);
	}
	glutSwapBuffers();
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
	gluPerspective(45, ratio, 0.1, 1000);
	// Get Back to the Modelview
	glMatrixMode(GL_MODELVIEW);
}

int main(int argc, char **argv) {

	//init Glut and create window
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
	glutInitWindowPosition(winPosX, winPosY);
	glutInitWindowSize(winWidth, winHeight);
	glutCreateWindow("3D MAZE");
	init();
	//register callbacks
	glutDisplayFunc(renderScene);
	glutReshapeFunc(changeSize);
	glutIdleFunc(renderScene);
	//Keyboard functions
	glutKeyboardFunc(processNormalKeys);
	glutPassiveMotionFunc(mouseMove);
	//enter Glut event processing cyclei
	glutMainLoop();
	return 1;
}