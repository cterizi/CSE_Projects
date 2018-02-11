function [finalImage] = filterMesouFourier(image)
    fourier = fft2(image);
    fourier = real(fourier)
    [x, y] = size(image);

    xZeroPad = x + 4;
    yZeroPad = y + 4;

    for i = 1:xZeroPad
        for j = 1:yZeroPad
            tempImage(i,j) = -1;
        end
    end
    
    for i = 3:x + 2
        for j = 3:y + 2
           tempImage(i, j) = fourier(i - 2, j - 2); 
        end
    end
    
    for i = 3:x + 2
        A = [];
       for j = 3:y + 2
           if(tempImage(i + 1, j - 2) ~= -1)
               A = [A tempImage(i + 1, j - 2)];
           end
           if(tempImage(i + 1, j + 2) ~= -1)
               A = [A tempImage(i + 1, j + 2)];
           end
           if(tempImage(i + 2, j - 2) ~= -1)
                A = [A tempImage(i + 2, j - 2)];
           end
           if(tempImage(i + 2, j - 1) ~= -1)
                A = [A tempImage(i + 2, j - 1)];
           end
           if(tempImage(i + 2, j) ~= -1)
               A = [A tempImage(i + 2, j)];
           end
           if(tempImage(i + 2, j + 1) ~= -1)
               A = [A tempImage(i + 2, j + 1)];
           end
           if(tempImage(i + 2, j + 2) ~= -1)
               A = [A tempImage(i + 2, j + 2)];
           end
           if(tempImage(i, j + 2) ~= -1)
               A = [A tempImage(i, j + 2)];
           end
           if(tempImage(i, j - 2) ~= -1)
                A = [A tempImage(i, j - 2)];
           end
           if(tempImage(i - 1, j + 2) ~= -1)
               A = [A tempImage(i - 1, j + 2)];
           end
           if(tempImage(i - 1, j - 2) ~= -1)
               A = [A tempImage(i - 1, j - 2)];
           end
           if(tempImage(i - 2, j + 2) ~= -1)
                A = [A tempImage(i - 2, j + 2)];
           end
           if(tempImage(i - 2, j + 1) ~= -1)
                A = [A tempImage(i - 2, j + 1)];
           end
           if(tempImage(i - 2, j) ~= -1)
               A = [A tempImage(i - 2, j)];
           end
           if(tempImage(i - 2, j - 1) ~= -1)
               A = [A tempImage(i - 2, j - 1)];
           end
           if(tempImage(i - 2, j - 2) ~= -1)
               A = [A tempImage(i - 2, j - 2)];
           end
           if(tempImage(i + 1, j + 1) ~= -1)
                A = [A tempImage(i + 1, j + 1)];
           end
           if(tempImage(i + 1, j) ~= -1)
                A = [A tempImage(i + 1, j)];
           end
           if(tempImage(i + 1, j - 1) ~= -1)
                A = [A tempImage(i + 1, j - 1)];
           end
           if(tempImage(i, j + 1) ~= -1)
               A = [A tempImage(i, j + 1)];
           end
           if(tempImage(i, j - 1) ~= -1)
               A = [A tempImage(i, j - 1)];
           end
           if(tempImage(i - 1, j + 1) ~= -1)
                A = [A tempImage(i - 1, j + 1)];
           end
           if(tempImage(i - 1, j) ~= -1)
               A = [A tempImage(i - 1, j)];
           end
           if(tempImage(i - 1, j - 1) ~= -1)
               A = [A tempImage(i - 1, j - 1)];
           end
           if(tempImage(i, j) ~= -1)
            A = [A tempImage(i, j)];
           end
           
           %sumList = sum(A);
           %sizeList = 25;
           mesosValue = mean(A);
           finalImage(i - 2, j - 2) = mesosValue;
           A = [];
       end
    end
    finalImage = real(ifft2(finalImage));
    colormap(gray);
    imshow(finalImage, []);
end