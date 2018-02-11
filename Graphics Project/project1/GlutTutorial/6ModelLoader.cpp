
#include<stdio.h>
#include <GL/gl.h>
#include <GL/glu.h>
#include <GL/glut.h>

//globals


GLuint objf;
float objfrot;


void loadObj(char *fname)
{
   FILE *fp;
   int read;
   GLfloat x, y, z;
   char ch;
   objf=glGenLists(1);
   fp=fopen(fname,"r");
   if (!fp)
  {
    printf("can't open file %s\n", fname);
    //exit(1);
  }
   glPointSize(2.0);
   glNewList(objf, GL_COMPILE);
   {
    glPushMatrix();
    glBegin(GL_POINTS);
    while(!(feof(fp)))
    {
     read=fscanf(fp,"%c %f %f %f",&ch,&x,&y,&z);
     if(read==4&&ch=='v')
    {
     glVertex3f(x,y,z);
     }
   }
   glEnd();
   }
   glPopMatrix();
   glEndList();
   fclose(fp);
}
//.obj loader code ends here
void reshape(int w,int h)
{ 
   glViewport(0,0,w,h);
   glMatrixMode(GL_PROJECTION);
   glLoadIdentity();
   gluPerspective (60, (GLfloat)w / (GLfloat)h, 0.1, 1000.0);
   //glOrtho(-25,25,-2,2,0.1,100);
   glMatrixMode(GL_MODELVIEW);
}


void drawObjf()
{
   glPushMatrix();
   glTranslatef(0.0f,-0.5f,-1.5f);
   glColor3f(1.0,0.23,0.27);
   glScalef(0.1,0.1,0.1);
   glRotatef(objfrot,0,1,0);
   glCallList(objf);
   glPopMatrix();
   objfrot=objfrot+0.6;
   if(objfrot>360)objfrot=objfrot-360;
}


void display(void)
{
glClearColor (0.0,0.0,0.0,1.0);
glClear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
glLoadIdentity();
drawObjf();
glutSwapBuffers(); //swap the buffers
}


int main(int argc,char **argv)
{
glutInit(&argc,argv);
glutInitDisplayMode(GLUT_DOUBLE|GLUT_RGB|GLUT_DEPTH);
glutInitWindowSize(800,450);
glutInitWindowPosition(20,20);
glutCreateWindow("ObjLoader");
glutReshapeFunc(reshape);
glutDisplayFunc(display);
glutIdleFunc(display);
loadObj("test.obj");//replace porsche.obj with radar.obj or any other .obj to display it
glutMainLoop();
return 0;
}

