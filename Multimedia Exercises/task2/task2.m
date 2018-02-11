%step 1
%read frame 0 and frame 1
fin0 = fopen('frame0.raw', 'r');
frame0 = fread(fin0, [176, 144])';
%imagesc(frame0)

fin1 = fopen('frame1.raw', 'r');
frame1 = fread(fin1, [176, 144])';
%imagesc(frame1)

%step 1.0
%27, 35, 42
QP = 35;
fprintf('----------------------- QP = %d  -----------------------\n', QP)

%step 1.1
%print entropy for original frame 0
fprintf('Entropy for original frame 0 : %d\n', entropy(frame0))

%step 2
%step 2.1
%integer approach of the two-dimensional DCT in each block
dct2Frame0 = zeros(144, 176);
for i = 1:4:144
    for j = 1:4:176
        dct2Frame0(i : i + 3, j : j + 3) = integer_transform(frame0(i : i + 3, j : j + 3));
    end
end

%step 2.2
%quantum
Q_ = zeros(144, 176);
for i = 1:4:144
    for j = 1:4:176
        Q_(i : i + 3, j : j + 3) = quantization(dct2Frame0(i : i + 3, j : j + 3), QP);
    end
end

%step 2.3
%entropy of the absolute value of the quantized coefficients
%print entropy for frame 0 after quantum
fprintf('Entropy for quantified frame 0 : %d\n', entropy(uint8(abs(Q_))))


%step 2.4
%reverse quantum
QInverse_ = zeros(144, 176);
for i = 1:4:144
    for j = 1:4:176
        QInverse_(i : i + 3, j : j + 3) = inv_quantization(Q_(i : i + 3, j : j + 3), QP);
    end
end

%step 2.5
%inverse transformation
dct2Frame0Inverse = zeros(144, 176);
for i = 1:4:144
    for j = 1:4:176
        dct2Frame0Inverse(i : i + 3, j : j + 3) = inv_integer_transform(QInverse_(i : i + 3, j : j + 3));
    end
end

%step 2.6
%post-scaling
finalFrame0 = round(dct2Frame0Inverse./64);

%step 2.7
%print entropy for final image
fprintf('Entropy for final frame 0 : %d\n', entropy(finalFrame0))

%step 2.8
%calculation of PSNR
MSE = sum(sum((finalFrame0 - frame0).*(finalFrame0 - frame0)))/(144*176);
if(MSE == 0)
    disp('MSE = 0')
else
    PSNR = 10 * log10(255^2/MSE);
end
fprintf('PSNR for final frame 0 : %d\n', PSNR)

%step 3
%step 3.1
%make all combinations for x = (-8,8) and y = (-8, 8)
x = [];
y = [];
for i = 1:8
   x = [x, i, -i];
   y = [y, i, -i];
end
x = [x, 0];
y = [y, 0];
x = sort(x);
y = sort(y);

row = 1;
allComb = [];
for i = 1:17
   for j = 1:17
       allComb(row,1) = x(1,i);
       allComb(row, 2) = y(1,j);
       row = row + 1;
   end
end

%step 3.2
%find a motion vector per macroblock
[a, b] = size(allComb);
motionVector = [];
rowMV = 1;
for k = 1:16:144
     for l = 1:16:176
        frame1MB = frame1(k : k + 15, l : l + 15);
        minValueOfSad = 1000000000;
        minX = 200;
        minY = 200;
        for i = 1:a
            vectorX = allComb(i, 1);
            vectorY = allComb(i, 2);
            if(k + vectorX >= 1 && k + vectorX + 15 <= 144)
                if(l + vectorY >= 1 && l + vectorY + 15 <= 176)
                    frame0MB = finalFrame0(k + vectorX:k + vectorX + 15, l + vectorY:l + vectorY + 15);
                    %sad calculation
                    difference = abs(frame1MB - frame0MB);
                    sad = sum(sum(difference));
                    if(sad < minValueOfSad)
                        minValueOfSad = sad;
                        minX = vectorX;
                        minY = vectorY;
                    end
                end
            end
        end
        motionVector(rowMV, 1) = minX;
        motionVector(rowMV, 2) = minY;
        rowMV = rowMV + 1;
     end
end

%step 4
%step 4.1
%Perform motion compensation and create a prediction of frame 1 using the 
%reconstructed frame 0 and the motion vectors
forecastFrame1 = [];
rowForMV = 0;
for i = 1:16:144
   for j = 1:16:176
       rowForMV = rowForMV + 1;
       vX = motionVector(rowForMV,1);
       vY = motionVector(rowForMV,2);
       takeFrame0 = finalFrame0(i + vX : i + vX + 15, j + vY : j + vY + 15);
       forecastFrame1(i : i + 15, j : j + 15) = takeFrame0;
   end
end

%step 4.2
%calculate the forecast error
forecastError = frame1 - forecastFrame1;
fprintf('Forecast error between new frame 1 and original frame 1 : %d\n', sum(sum(forecastError)))

%step 4.3
%Divide the prediction error into a block of 4 × 4 size, take 
%the integer approach of the two-dimensional DCT in each block and 
%quantify according to H.264
forecastErrorMatrix = frame1 - forecastFrame1;
integerApproachFromErrorFrame1 = zeros(144, 176);
for i = 1:4:144
   for j = 1:4:176
       integerApproachFromErrorFrame1(i : i + 3, j : j + 3) = integer_transform(forecastErrorMatrix(i : i + 3, j : j + 3));
   end
end

quantizationMatrixErrorFrame1 = zeros(144, 176);
for i = 1:4:144
   for j = 1:4:176
       quantizationMatrixErrorFrame1(i : i + 3, j : j + 3) = quantization(integerApproachFromErrorFrame1(i : i + 3, j : j + 3), QP);
   end
end

%step 4.4
%entropy of the absolute value of the quantized coefficients
%print entropy for forecast error for frame 1 after quantum
fprintf('Entropy for forecast error for frame 1 after quantum : %d\n', entropy(uint8(abs(quantizationMatrixErrorFrame1))))

%step 4.5
%%reverse quantum
QInverseErrorFrame1_ = zeros(144, 176);
for i = 1:4:144
    for j = 1:4:176
        QInverseErrorFrame1_(i : i + 3, j : j + 3) = inv_quantization(quantizationMatrixErrorFrame1(i : i + 3, j : j + 3), QP);
    end
end

%step 4.6
%inverse transformation
dct2Frame1Inverse = zeros(144, 176);
for i = 1:4:144
    for j = 1:4:176
        dct2Frame1Inverse(i : i + 3, j : j + 3) = inv_integer_transform(QInverseErrorFrame1_(i : i + 3, j : j + 3));
    end
end

%step 4.7
%post-scaling
fFrame1 = round(dct2Frame1Inverse./64);

%step 4.8
%recreate frame 1
recreateFrame1_ = fFrame1 + forecastFrame1;

%step 4.9
%calculate PSNR for frame 1
MSE = sum(sum((recreateFrame1_ - frame1).*(recreateFrame1_ - frame1)))/(144*176);
if(MSE == 0)
    disp('MSE = 0')
else
    PSNR = 10 * log10(255^2/MSE);
end
fprintf('PSNR for final frame 1 : %d\n', PSNR)

%imagesc(finalFrame0)
%imagesc(recreateFrame1_)
fclose(fin0);
fclose(fin1);