function [finalImage] = filterGoussian(image, D0)
    [x, y] = size(image);
    dcenterX = round(x/2);
    dcenterY = round(y/2);
    
    H = zeros(x, y);
    
    for i = 1:x
       for j = 1:y
           D = (i - dcenterX).^2 + (j - dcenterY).^2;
           p = power(D0,2);
           d = (-1)*D;
           pm = 2 * p;
           e = d/pm;
           H(i, j) = exp(e);
       end
    end
    
    F = fft2(image);
    Fshifted = fftshift(F);
    Ffiltered = Fshifted.*H;
    %F2 = log(1 + Ffiltered);
    finalImage = real(ifft2(ifftshift(Ffiltered)));
    imshow(real(ifft2(ifftshift(Ffiltered))), [])
    %imshow(ifft2(ifftshift(Ffiltered)), []);
end