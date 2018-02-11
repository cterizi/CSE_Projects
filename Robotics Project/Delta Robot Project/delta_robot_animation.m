function [] = delta_robot_animation()

    close all;
    rotate3d on
    
    %==========================
    %==========================
    %init delta robot variables
    %==========================
    %==========================
    f = 3; %base length
    e = 1; %platform length
    rf = 1.2; %arm1 length
    re = 2; %arm2 length
    
    wB = sqrt(3)*f/6;
    uB = sqrt(3)*f/3;
    wP = sqrt(3)*e/6;
    uP = sqrt(3)*e/3;
   
    %======================
    %======================
    %degrees to rad convert
    %======================
    %======================
    use_inverse_kinematics = 1;
    q1 = 90;
    q2 = 60;
    q3 = 50;
    q1 = q1*pi/180;
    q2 = q2*pi/180;
    q3 = q3*pi/180;
    x = 1.2;
    y = 1.01;
    z = -2.6;
    cos120 = cos(120*pi/180);
    sin120 = sin(120*pi/180);
    cos30 = cos(30*pi/180);
    sin30 = sin(30*pi/180);
    tan60 = tan(60*pi/180);
    
    %===================
    %===================
    %init J coordinates
    %===================
    %===================
    
    t = (f-e)/(sqrt(2)*3);
    %J1
    x1 = 0;
    y1 = -(t + rf*cos(q1));
    z1 = -rf*sin(q1);
    J1 = [x1, y1, z1];
    
    %J2
    y2 = (t + rf*cos(q2))*sin30;
    x2 = y2*tan60;
    z2 = -rf*sin(q2);
    J2 = [x2, y2, z2];
    
    %J3
    y3 = (t + rf*cos(q3))*sin30;
    x3 = -y3*tan60;
    z3 = -rf*sin(q3);
    J3 = [x3, y3, z3];
    if (use_inverse_kinematics == 1)
        %=====================
        %=====================
        %inverse kinematics
        %=====================
        %=====================
        x_2 = x*cos120 + y*sin120;
        y_2 = -x*sin120 + y*cos120;
        cos_120 = cos(-120*pi/180);
        sin_120 = sin(-120*pi/180);
        x_3 = x*cos_120 + y*sin_120;
        y_3 = -x*sin_120 + y*cos_120;

        q1 = delta_robot_inverse_kinematics(x, y, z, e, f, rf, re)
        q2 = delta_robot_inverse_kinematics(x_2, y_2, z, e, f, rf, re)
        q3 = delta_robot_inverse_kinematics(x_3, y_3, z, e, f, rf, re)
        q1 = q1*pi/180;
        q2 = q2*pi/180;
        q3 = q3*pi/180;
        %===================
        %===================
        %recalculate J
        %===================
        %===================

        t = (f-e)/(sqrt(2)*3);
        %J1
        x1 = 0;
        y1 = -(t + rf*cos(q1));
        z1 = -rf*sin(q1);
        J1 = [x1, y1, z1];

        %J2
        y2 = (t + rf*cos(q2))*sin30;
        x2 = y2*tan60;
        z2 = -rf*sin(q2);
        J2 = [x2, y2, z2];

        %J3
        y3 = (t + rf*cos(q3))*sin30;
        x3 = -y3*tan60;
        z3 = -rf*sin(q3);
        J3 = [x3, y3, z3];
    else
        [x, y, z] = delta_robot_forwarad_kinematics(q1, q2, q3, f, e, rf, re, wB, uB, J1, J2, J3, wP, uP);
    end
    
    t = (f-e)/(sqrt(2)*3);
    J1_ = [0 -t-rf*cos(q1) -rf*sin(q1)];
    J2_ = [[t+rf*cos(q2)]*cos30 [t+rf*cos(q2)]*sin30 -rf*sin(q2)];
    J2_ = [[t+rf*cos(q3)]*cos30 [t+rf*cos(q3)]*sin30 -rf*sin(q3)];
        
    %=====================
    %=====================
    %draw base
    %=====================
    %=====================
    b1B = [f/2 -wB 0];
    b2B = [0 uB 0];
    b3B = [-f/2 -wB 0];
    
    B1B = [0 -wB 0];
    B2B = [sqrt(3)*wB/2 wB/2 0];
    B3B = [-sqrt(3)*wB/2 wB/2 0];
    
    drawLine([b1B(1),b2B(1)], [b1B(2),b2B(2)], [0, 0], 'r');
    drawLine([b1B(1),b3B(1)], [b1B(2),b3B(2)], [0, 0], 'r');
    drawLine([b3B(1),b2B(1)], [b3B(2),b2B(2)], [0, 0], 'r');
    
    %=====================
    %=====================
    %draw first arms
    %=====================
    %=====================
    drawLine([B1B(1), J1(1)], [B1B(2), J1(2)], [B1B(3), J1(3)], 'g');
    drawLine([B2B(1), J2(1)], [B2B(2), J2(2)], [B2B(3), J2(3)], 'g');
    drawLine([B3B(1), J3(1)], [B3B(2), J3(2)], [B3B(3), J3(3)], 'g');
    
    %=====================
    %=====================
    %draw second arms
    %=====================
    %=====================
    
    d1 = [0 uP 0];
    d2 = [-e/2 -wP 0];
    d3 = [e/2 -wP 0];
    E1 = [x,y,z] - d1;
    E2 = [x,y,z] - d2;
    E3 = [x,y,z] - d3;
    drawLine([J1(1), E1(1)], [J1(2), E1(2)], [J1(3), E1(3)], 'b');
    drawLine([J2(1), E2(1)], [J2(2), E2(2)], [J2(3), E2(3)], 'b');
    drawLine([J3(1), E3(1)], [J3(2), E3(2)], [J3(3), E3(3)], 'b');
    
    %=====================
    %=====================
    %draw platform
    %=====================
    %=====================
    drawLine([E1(1), E2(1)], [E1(2), E2(2)], [E1(3), E2(3)], 'k');
    drawLine([E1(1), E3(1)], [E1(2), E3(2)], [E1(3), E3(3)], 'k');
    drawLine([E3(1), E2(1)], [E3(2), E2(2)], [E3(3), E2(3)], 'k');

    xlabel('x');
    ylabel('y');
    zlabel('z');
    set(gca,'View',[30, 32]);
    axis([-5 5 -5 5 -5 5]); % Set scaling for the x- and y-axes.
    axis square; % Make the current axis box square in size. 
end