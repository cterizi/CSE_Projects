function [] = task1g()
    y = 230;
    x = randi([0 255], y, y); 
    F = fft2(x);
    figure, imshow(F,[])
    
    for i = 0: y - 1
       for j = 0: y - 1
           G(i + 1, j + 1) = (-1)^(i + j);
       end
    end
    
    R = x .* G;
    figure, imshow(R, [])
    
    FF = fft2(R);
    figure, imshow(FF, [])
end