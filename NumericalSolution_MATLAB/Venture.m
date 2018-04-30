classdef Venture
    properties
        Name
        
    end
    methods
        function obj = Venture(str)
            if ~isa(str,'string')
                error('Name property must be a string');
            else
            obj.Name = str;
            end
        end
        function fig_handle = plot(obj)
        end
        function TotalCost
        end
        function TotalSell
        end
        function ProfitMargin
        end
    end
end

