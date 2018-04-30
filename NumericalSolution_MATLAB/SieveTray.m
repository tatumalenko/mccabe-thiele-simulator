classdef SieveTray
    %----------------------------------------
    % PROPERTIES
    %----------------------------------------
    properties (Constant)
        CEPCI_2014 = 579.7;
        CEPCI_1996 = 382;
    end
    properties
        Material
        Diameter
        NumberOfTrays
    end
    properties (Dependent)
        Fq
        Fbm
        Cbm
        Cbm0
        Cp
        Ctm
        Cgr
    end
    %----------------------------------------
    % METHODS
    %----------------------------------------
    methods
        function value = get.Cp(obj)
            % Cp (Capital Cost)
            value = 235 + 19.80*obj.Diameter + 75.07*obj.Diameter^2;
        end
        %----------------------------------------
        function value = get.Fq(obj)
            if		(obj.NumberOfTrays >= 1 && obj.NumberOfTrays < 2.5) 
                value = 3.0;
            elseif	(obj.NumberOfTrays >= 2.5  && obj.NumberOfTrays < 5.0)
                value = 2.5;
            elseif	(obj.NumberOfTrays >= 5.0  && obj.NumberOfTrays < 8.5)
                value = 2.0;
            elseif	(obj.NumberOfTrays >= 8.5  && obj.NumberOfTrays < 20)
                value = 1.5;
            elseif	(obj.NumberOfTrays >= 20), value = 1;
            end
        end
        %----------------------------------------
        function value = get.Fbm(obj)
            % Fbm (Bare Module Factor)
            switch(obj.Material)
                case 'Carbon steel'
                    value = 1.2;
                case 'Stainless steel'
                    value = 2.0;
                case 'Nickel alloy'
                    value = 5.0;
            end
%             if (strcmp(obj.Material,'Carbon steel')
        end
        %----------------------------------------
        function value = get.Cbm(obj)
            % Cbm (Bare Module Cost)
            value = obj.Cp*obj.NumberOfTrays*obj.Fbm*obj.Fq*(obj.CEPCI_2014/obj.CEPCI_1996);
        end
        %----------------------------------------
        function value = get.Cbm0(obj)
            % Cbm (Bare Module Cost - Base Conditions)
            Fbm0 = 1.2;
            value = obj.Cp*obj.NumberOfTrays*Fbm0*obj.Fq*(obj.CEPCI_2014/obj.CEPCI_1996);
        end
        %----------------------------------------
        function value = get.Ctm(obj)
            value = 1.18*obj.Cbm;
        end
        %----------------------------------------
        function value = get.Cgr(obj)
            value = obj.Ctm + 0.5*obj.Cbm0;
        end
    end
end
