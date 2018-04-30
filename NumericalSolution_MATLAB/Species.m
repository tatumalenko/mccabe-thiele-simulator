classdef Species
    properties
        NormalBP        % K
        HeatCapacity    % kJ/kmol.K
        LatentHeat      % kJ/mol
    end
    methods
        function obj = Species(str)
            if strcmp(str,'Fancy Scarves')
                obj.HeatCapacity = FancyScarves.HeatCapacity;
                obj.NormalBP = FancyScarves.NormalBP;
                obj.LatentHeat = FancyScarves.LatentHeat;

            elseif strcmp(str,'Fancy Ties')
                obj.HeatCapacity = FancyTies.HeatCapacity;
                obj.NormalBP = FancyTies.NormalBP;
                obj.LatentHeat = FancyTies.LatentHeat;

            elseif strcmp(str,'Cocoa')
                obj.HeatCapacity = Cocoa.HeatCapacity;
                obj.NormalBP = Cocoa.NormalBP;
                obj.LatentHeat = Cocoa.LatentHeat;

            elseif strcmp(str,'Sugar')
                obj.HeatCapacity = Sugar.HeatCapacity;
                obj.NormalBP = Sugar.NormalBP;
                obj.LatentHeat = Sugar.LatentHeat;

            elseif strcmp(str,'Watches')
                obj.HeatCapacity = Watches.HeatCapacity;
                obj.NormalBP = Watches.NormalBP;
                obj.LatentHeat = Watches.LatentHeat;

            elseif strcmp(str,'iPads')
                obj.HeatCapacity = iPads.HeatCapacity;
                obj.NormalBP = iPads.NormalBP;
                obj.LatentHeat = iPads.LatentHeat;
            end
        end
    end
end