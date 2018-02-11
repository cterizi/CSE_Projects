function [finalImage] = bonus(image, D0, W)
    [x, y] = size(image);
    
    dCenterX = round(x/2);
    dCenterY = round(y/2);
    
    %Filtro - Ideato Zwnofraktiko
    for i = 1:x
       for j = 1:y
           d = sqrt((i - dCenterX)^2 + (j - dCenterY)^2);
           if(d < (D0 - W/2))
               H(i,j) = 1;
           else
               if (d > D0 + W/2)
                   H(i,j) = 1;
               else
                   H(i,j) = 0;
               end
           end
       end
    end
    
    F = fft2(image);
    Fshifted = fftshift(F);
    Ffiltered = Fshifted.*H;
    finalImage = real(ifft2(ifftshift(Ffiltered)));
    finalImage = uint8(finalImage);
    imshow(finalImage, [])
    
    %Filtro - Butterworth
%     for i = 1:x
%        for j = 1:y
%            d = sqrt((i - dCenterX)^2 + (j - dCenterY)^2);
%            G(i,j) = 1/(1 + (D0_/d)^(2*n));
%        end
%     end
%     F1 = fft2(finalImage);
%     Fshifted1 = fftshift(F1);
%     Ffiltered1 = Fshifted1.*G;
%     finalImage1 = real(ifft2(ifftshift(Ffiltered1)));
%    
%     %imshow(finalImage1, [])
%     %plot(abs(Fshifted1));
%     Fshifted1(abs(Fshifted1) <= (3.5)*10^5) = 0;
%     %Ffiltered1 = Fshifted1.*G;
%     finalImage1 = real(ifft2(ifftshift(Ffiltered1)));
    %imshow(finalImage1, [])
end