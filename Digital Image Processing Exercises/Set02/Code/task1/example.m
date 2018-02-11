function [finalImage] = example(image, n, D0_)
    [x, y] = size(image);
    
    dCenterX = round(x/2);
    dCenterY = round(y/2);
    
    for i = 1:x
       for j = 1:y
           d = sqrt((i - dCenterX)^2 + (j - dCenterY)^2);
           G(i,j) = 1/(1 + (d/D0_)^(2*n));
       end
    end
    F = fft2(image);
    Fshifted = fftshift(F);
    Ffiltered = Fshifted.*G;
    finalImage = real(ifft2(ifftshift(Ffiltered)));
    imshow(finalImage, [])
end