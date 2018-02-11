function [] = drawLine(X, Y, Z, color)
    hold on;
    plot3(X,Y,Z,color, 'Marker','.');
end