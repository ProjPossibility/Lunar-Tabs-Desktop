function [sol,check] = solveUnigramProb(N)

%write out polynomial
poly_roots = zeros(1,N);
for i=1:N
    poly_roots(i) = (N-i+1);
end
poly_roots = [fliplr(poly_roots),-1]; %constrain to sum to 1.
feas_sol = roots(poly_roots);

%keep only truely feasible solutions
feasible = imag(feas_sol)==0 & real(feas_sol) >= 0 & real(feas_sol) <= 1;
sol=feas_sol(feasible);

%check solution
check=0;
for i=1:N
    check = check + sol.^(i) .* (N-i+1);
end