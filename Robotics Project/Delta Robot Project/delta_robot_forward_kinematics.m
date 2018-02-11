function [x, y, z] = delta_robot_forward_kinematics(q1, q2, q3, f, e, rf, re, wB, uB, J1, J2, J3, wP, uP)
    
    x1 = J1(1);
    y1 = J1(2);
    z1 = J1(3);
    x2 = J2(1);
    y2 = J2(2);
    z2 = J2(3);
    x3 = J3(1);
    y3 = J3(2);
    z3 = J3(3);
    
    d = (y2-y1)*x3 - (y3-y1)*x2;
    
    w1 = x1^2 + y1^2 + z1^2;
    w2 = x2^2 + y2^2 + z2^2;
    w3 = x3^2 + y3^2 + z3^2;
    z21 = z2 - z1;
    y31 = y3 - y1;
    y21 = y2 - y1;
    z31 = z3 - z1;
    w21 = w2 - w1;
    w31 = w3 - w1;
    a1 = (1/d)*(z21*y31 - z31*y21);
    a2 = -(1/d)*(z21*x3 - z31*x2);
    b1 = -(1/(2*d))/(w21*y31 - w31*y21);
    b2 = (1/(2*d))/(w21*x3 - w31*x2);
    a = a1^2 + a2^2 + 1;
    b = 2*(a1 + a2*(b2-y1) - z1);
    c = z1^2 - re^2;
    
    %===================
    %===================
    %end effector points
    %===================
    %===================
    z = (-b - sqrt(b^2 - 4*a*c))/(2*a);
    x = a1*z + b1;
    y = a2*z + b2;
    
end