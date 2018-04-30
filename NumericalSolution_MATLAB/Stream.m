classdef Stream 
    %----------------------------------------
    % PROPERTIES
    %----------------------------------------
    properties 
        ComponentName
        ComponentFrac       % mol/mol
        Temperature
        Flow                % kmol/h
        Cost                % CDN$/kmol
    end
    properties (Dependent)
        ComponentHeatCapacity
        HeatCapacity
        ComponentNormalBP
        NormalBP
        ComponentLatentHeat
        LatentHeat
    end
    %----------------------------------------
    % METHODS
    %----------------------------------------
    methods
        %----------------------------------------
        function obj = set.Flow(obj,val)
            obj.Flow = val;
        end
        function obj = set.ComponentName(obj,val)
            for i = 1:length(val)
                obj.ComponentName{i} = val{i};
            end
        end
        %----------------------------------------
        function value = get.ComponentHeatCapacity(obj)
            value = zeros(1,length(obj.ComponentName));
            for i = 1:length(obj.ComponentName)
                species = Species(obj.ComponentName{i});
                value(i) = species.HeatCapacity;
            end
        end
        %----------------------------------------
        function value = get.ComponentNormalBP(obj)
            value = zeros(1,length(obj.ComponentName));
            for i = 1:length(obj.ComponentName)
                species = Species(obj.ComponentName{i});
                value(i) = species.NormalBP;
            end
        end
        %----------------------------------------
        function value = get.ComponentLatentHeat(obj)
            value = zeros(1,length(obj.ComponentName));
            for i = 1:length(obj.ComponentName)
                species = Species(obj.ComponentName{i});
                value(i) = species.LatentHeat;
            end
        end
        %----------------------------------------
        function value = get.HeatCapacity(obj)
            value = sum(obj.ComponentFrac.*obj.ComponentHeatCapacity);
        end
        %----------------------------------------
        function value = get.NormalBP(obj)
            value = sum(obj.ComponentFrac.*obj.ComponentNormalBP);
        end
        %----------------------------------------
        function value = get.LatentHeat(obj)
            value = sum(obj.ComponentFrac.*obj.ComponentLatentHeat);
        end
    end
end