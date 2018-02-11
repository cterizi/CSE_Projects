function [] = directMethodEuler(N)

format long

boundA = 0;
boundB = 10;
h = 10/N;

Xt_ = 1;
Yt_ = 1;

for i = boundA:h:boundB
    newX = Xt_ + h*(-11*Xt_ + 100*Yt_);
    newY = Yt_ + h*(Xt_ - 11*Yt_);
    Xt_ = newX;
    Yt_ = newY;
end

Xt_;
Yt_;

end