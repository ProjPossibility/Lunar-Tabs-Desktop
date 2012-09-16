function s = LBDM_strengths(arr)

%compute r
r = zeros(length(arr),1);
for i=1:length(r)        
    
    %parts
    x_i = arr(i);
    if(i==length(r))
        x_ip1 = 0;
    else
        x_ip1 = arr(i+1);
    end
    
    %equation
    if(x_i >= 0 && x_ip1 >= 0 && (x_i+x_ip1)~=0)
        r(i) = abs(x_i-x_ip1) / (x_i+x_ip1);
    end
    
end

%compute strengths
s = zeros(length(arr),1);
for i=1:length(s)    
    if(i==1)
        s(i) = arr(i) * (r(i));
    else
        s(i) = arr(i) * (r(i-1)+r(i));    
    end
end

%normalize between [0,1].
s = s ./ sum(s);