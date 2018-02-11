function [q] = delta_robot_inverse_kinematics(x, y, z, e, f, rf, re)

    y = -0.5*0.57735 * e;
    y1 = -0.5*0.57735 * f;

    a = (x^2 + y^2 + z^2 + rf^2 - re^2 - y1^2)/(2*z);
    b = (y1-y)/z;
    d = -(a+b*y1)*(a+b*y1)+rf*(b*b*rf+rf); 
    if d < 0
       disp('invalid x,y,z');
       q = -1;
       return;
    end

    yj = (y1 - a*b - sqrt(d))/(b*b + 1);
    zj = a + b*yj;
    t = 0;
    if (yj > y1)
       t = 180.0;
    end
    q = 180.0*atan(-zj/(y1 - yj))/pi + t;

end