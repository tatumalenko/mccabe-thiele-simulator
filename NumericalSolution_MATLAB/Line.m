classdef Line
    properties
        Slope
        Intercept
    end
    %% METHODS
    methods
        function obj = Line(varargin)
            if isempty(varargin)
                obj.Slope = 0;
                obj.Intercept = 0;
            else
                obj.Slope        = varargin{1};
                obj.Intercept    = varargin{2};
            end
        end
        function value = calcX(obj, y)
            value = (y - obj.Intercept)./obj.Slope;
        end
        function value = calcY(obj, x)
            value = obj.Slope.*x + obj.Intercept;
        end
    end

    
    methods (Static)
        function pt = calcIntersection(lf1,lf2)
            x = (lf1.Intercept - lf2.Intercept)/(lf2.Slope - lf1.Slope);
            y = lf2.Slope*x + lf2.Intercept;
            pt.x = x;
            pt.y = y;
        end
    end
end