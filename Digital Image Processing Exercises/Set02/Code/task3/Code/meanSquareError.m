function [err] = meanSquareError(inputImage, image)
    [x,y] = size(inputImage);
    athroisma = sum(sum((inputImage - image).^2));
    err = athroisma/(x*y);
end