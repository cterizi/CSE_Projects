function [] = task1(N)
    %Step 1
    f = imread('cameraman.tif');
    imagesc(f);
    colormap(gray);
    
    %Step 2
    H = entropy(f);
    fprintf('Entropy : %d\n', H)
    
    %Step 3, 4
    [a, b] = size(f);
    
    DCTmatrix = zeros(a);
    for x = 1:8:a
        for y = 1:8:a
            DCTmatrix(x:x+7, y:y+7) = dct2(f(x:x+7, y:y+7));
        end
    end
    
    %Step 5
    Q1 = N.*[16 11 10 16 24 40 51 61;
            12 12 14 19 26 58 60 55;
            14 13 16 24 40 57 69 56;
            14 17 22 29 51 87 80 62;
            18 22 37 56 68 109 103 77;
            24 35 55 64 81 104 113 92;
            49 64 78 87 103 121 120 101;
            72 92 95 98 112 100 103 99];
    
    F_ = zeros(a);
    for x1 = 1:8:a
        for y1 = 1:8:a
            F_(x1:x1+7, y1:y1+7) = round(DCTmatrix(x1:x1+7, y1:y1+7)./Q1);
        end
    end
    
    %Step 6
    F_absoluteValue = abs(F_);
    H_ = entropy(F_absoluteValue);
    fprintf('Entropy |F_|: %d\n', H_)
    
    %Step 7
    F__ = zeros(a);
    for x2 = 1:8:a
        for y2 = 1:8:a
            F__(x2:x2+7, y2:y2+7) = F_(x2:x2+7, y2:y2+7).* Q1;
        end
    end
    
    %Step 8
    DCTInversematrix = zeros(a);
    for x3 = 1:8:a
        for y3 = 1:8:a
            DCTInversematrix(x3:x3+7, y3:y3+7) = idct2(F__(x3:x3+7, y3:y3+7));
        end
    end
    
    %Step 9
    finalImage = uint8(DCTInversematrix);
    
    %Step 10
    MSE = (sum(sum((finalImage-f).*(finalImage-f)))/(a*b));
    if(MSE == 0)
       disp('MSE = 0') 
    else
        PSNR = 10 * log10(255^2/MSE)
    end
    
    %Step 11
    HFinalImage = entropy(finalImage);
    fprintf('Entropy final image: %d\n', HFinalImage)
    
    %Step 12
    imagesc(finalImage);
    colormap(gray);
    
end