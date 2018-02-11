function [finalImage] = filterMedian(image)
    [x, y] = size(image);

    xZeroPad = x + 2;
    yZeroPad = y + 2;

    for i = 1:xZeroPad
        for j = 1:yZeroPad
            tempImage(i,j) = -1;
        end
    end
    
    for i = 2:x + 1
        for j = 2:y + 1
           tempImage(i, j) = image(i - 1, j - 1); 
        end
    end
    
    finalImage = image;
    
    for i = 2:x + 1
        A = [];
       for j = 2:y + 1
           if(tempImage(i, j) ~= -1)
            A = [A tempImage(i, j)];
           end
           if(tempImage(i - 1, j - 1) ~= -1)
            A = [A tempImage(i - 1, j - 1)];
           end
           if(tempImage(i - 1, j) ~= -1)
            A = [A tempImage(i - 1, j)];
           end
           if(tempImage(i - 1, j + 1) ~= -1)
               A = [A tempImage(i - 1, j + 1)];
           end
           if(tempImage(i, j - 1) ~= -1)
               A = [A tempImage(i, j - 1)];
           end
           if(tempImage(i, j + 1) ~= -1)
               A = [A tempImage(i, j + 1)];
           end
           if(tempImage(i + 1, j - 1) ~= -1)
                A = [A tempImage(i + 1, j - 1)];
           end
           if(tempImage(i + 1, j) ~= -1)
            A = [A tempImage(i + 1, j)];
           end
           if(tempImage(i + 1, j + 1) ~= -1)
            A = [A tempImage(i + 1, j + 1)];
           end
           
           A = sort(A);
           medianValue = median(A);
           finalImage(i - 1, j - 1) = medianValue;
           A = [];
       end
    end
    colormap(gray);
    imagesc(finalImage)
end