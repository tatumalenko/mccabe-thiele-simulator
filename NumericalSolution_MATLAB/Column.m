classdef Column
    %----------------------------------------
    % PROPERTIES
    %----------------------------------------
    properties (Constant)
        CEPCI_2014 = 579.7;
        CEPCI_1996 = 382;
    end
    properties
        Diameter            % m
        Length              % m
        PressureGauge       % bar gauge
        RefluxRatio         
        Material            
        Tray
        Feed
        Distillate
        Bottoms
    end
    properties (Dependent)
        FeedQuality
        QLine
        EnrichLine
        StripLine
        XIntersection
        YIntersection
        NumberOfTrays
        Cp
        Cbm0
        Cbm
        Ctm
        Cgr
        TotalCost
        StreamProfit
        ProfitPBP1
    end
    %----------------------------------------
    % METHODS
    %----------------------------------------
    methods
        function obj = Column
            obj.Feed          = Stream;
            obj.Distillate    = Stream;
            obj.Bottoms       = Stream;
            obj.Tray          = SieveTray; 
        end
        %----------------------------------------
        function value = get.FeedQuality(obj)
            value = (obj.Feed.HeatCapacity*(obj.Feed.NormalBP ...
                - obj.Feed.Temperature) + obj.Feed.LatentHeat)/ ...
                obj.Feed.LatentHeat;
        end
        %----------------------------------------
        function value = get.QLine(obj)
            value = Line;
            value.Slope = obj.FeedQuality/(obj.FeedQuality - 1);
            value.Intercept = -obj.Feed.ComponentFrac(1)/ ...
                (obj.FeedQuality - 1);
        end
        %----------------------------------------
        function value = get.EnrichLine(obj)
            value = Line;
            value.Slope = obj.RefluxRatio/(obj.RefluxRatio + 1);
            value.Intercept = obj.Distillate.ComponentFrac(1)/ ...
                (obj.RefluxRatio + 1);
        end
        %----------------------------------------
        function value = get.StripLine(obj)
            value = Line;
            value.Slope = (obj.YIntersection - ...
                obj.Bottoms.ComponentFrac(1))/(obj.XIntersection ...
                - obj.Bottoms.ComponentFrac(1));
            value.Intercept = obj.YIntersection ...
                - value.Slope*obj.XIntersection;
        end
        %----------------------------------------
        function value = get.XIntersection(obj)
            value = (obj.EnrichLine.Intercept - obj.QLine.Intercept) ...
                /(obj.QLine.Slope - obj.EnrichLine.Slope);
        end
        %----------------------------------------
        function value = get.YIntersection(obj)
            value = obj.QLine.Slope*obj.XIntersection ...
                + obj.QLine.Intercept;
        end
        %----------------------------------------
        function value = get.NumberOfTrays(obj)
            fXStrip = @(Y)(Y - obj.StripLine.Intercept) ...
                /obj.StripLine.Slope;
            fXEnrich = @(Y)(Y - obj.EnrichLine.Intercept)/ ...
                obj.EnrichLine.Slope;
            
            BEDobj     = BED(obj.Feed.ComponentName{1});
            fitPoly    = fit(BEDobj.X',BEDobj.Y','pchipinterp');
            % equlibrium stages
            lastX = obj.Bottoms.ComponentFrac(1);
            lastY = lastX;
            i = 1;
            while(lastX < obj.Distillate.ComponentFrac(1))
                ystage(i) = fitPoly(lastX);
                if ystage(i) >= obj.Distillate.ComponentFrac(1)
                    xstage(i) = ystage(i);
                elseif ystage(i) >= obj.YIntersection
                    xstage(i) = fXEnrich(ystage(i));
                else
                    xstage(i) = fXStrip(ystage(i));
                end
                lastX = xstage(i);
                lastY = ystage(i);
                i = i + 1;
            end
            value = i - 1;
        end
        %----------------------------------------
        function PlotStages(obj,hAx)
            fXStrip = @(Y)(Y - obj.StripLine.Intercept)/ ...
                obj.StripLine.Slope;
            fXEnrich = @(Y)(Y - obj.EnrichLine.Intercept)/ ...
                obj.EnrichLine.Slope;
            
            BEDobj     = BED(obj.Feed.ComponentName{1});
            fitPoly    = fit(BEDobj.X',BEDobj.Y','pchipinterp');
            
            % fitted polynomial and equilibrium data
            plot(hAx,BEDobj.X',BEDobj.Y','k*'); 
            hold on;
            plot(hAx,linspace(0,1,50),fitPoly(linspace(0,1,50)),'k');               
            
            % 45 degree line
            plot(hAx,[0 1],[0 1],'k');
            
            % StripLine
            plot(hAx,[obj.Bottoms.ComponentFrac(1),obj.XIntersection], ...
                [obj.Bottoms.ComponentFrac(1),obj.YIntersection],'k');
            
            % EnrichLine
            plot(hAx,[obj.Distillate.ComponentFrac(1), ...
                obj.XIntersection],[obj.Distillate.ComponentFrac(1), ...
                obj.YIntersection],'k');
            
            % equlibrium stages
            lastX = obj.Bottoms.ComponentFrac(1);
            lastY = lastX;
            i = 1;
            while(lastX < obj.Distillate.ComponentFrac(1))
                ystage(i) = fitPoly(lastX);
                if ystage(i) >= obj.Distillate.ComponentFrac(1)
                    xstage(i) = ystage(i);
                elseif ystage(i) >= obj.YIntersection
                    xstage(i) = fXEnrich(ystage(i));
                else
                    xstage(i) = fXStrip(ystage(i));
                end
                plot(hAx,[lastX,lastX],[lastY,ystage(i)],'k');
                plot(hAx,[lastX,xstage(i)],[ystage(i),ystage(i)],'k');
                lastX = xstage(i);
                lastY = ystage(i);
                i = i + 1;
            end
            %numberoftrays = i - 1;
            xlabel(hAx,'x (mole frac.)');
            ylabel(hAx,'y (mole frac.)');
            legend(hAx,{'pchip fit','data'},'location','southeast','FontSize',13);
        end
        %----------------------------------------
        function value = get.Cp(obj)
            % Cp (Capital Cost)
            if      (obj.Diameter() <= 0.3)
                k1 = 3.3392; k2 =-0.5538; k3 = 0.2851;
            elseif (obj.Diameter() <= 0.5 && obj.Diameter() >  0.3)
                k1 = 3.4746; k2 = 0.5893; k3 = 0.2053;
            elseif (obj.Diameter() <= 1.0 && obj.Diameter() >  0.5)
                k1 = 3.6237; k2 = 0.5262; k3 = 0.2146;
            elseif (obj.Diameter() <= 1.5 && obj.Diameter() >  1.0)
                k1 = 3.7559; k2 = 0.6361; k3 = 0.1069;
            elseif (obj.Diameter() <= 2.0 && obj.Diameter() >  1.5)
                k1 = 3.9484; k2 = 0.4623; k3 = 0.1717;
            elseif (obj.Diameter() <= 2.5 && obj.Diameter() >  2.0)
                k1 = 4.0547; k2 = 0.4620; k3 = 0.1558;
            elseif (obj.Diameter() <= 3.0 && obj.Diameter() >  2.5)
                k1 = 4.1110; k2 = 0.6094; k3 = 0.0490;
            elseif (obj.Diameter() <= 4.0 && obj.Diameter() >  3.0)
                k1 = 4.3919; k2 = 0.2859; k3 = 0.1842;
            end
            value = 10^(k1 + k2*log10(obj.Length) ...
                + k3*(log10(obj.Length))^2);
        end
        %----------------------------------------
        function [Fm,Fp] = calcFactors(obj)
            % Fp (Pressure Factor)
            if(obj.PressureGauge >= 3.7 && obj.PressureGauge < 400)
                Fp = 0.5146 + 0.6838*log10(obj.PressureGauge) ...
                    + 0.2970*(log10(obj.PressureGauge))^2 ...
                    + 0.0235*(log10(obj.PressureGauge))^6 ...
                    + 0.0020*(log10(obj.PressureGauge))^8;
            elseif(obj.PressureGauge >= -0.5 && obj.PressureGauge < 3.7)
                Fp = 1;
            elseif(obj.PressureGauge < -0.5)
                Fp = 1.25;
            end
            % Fm (Material Factor)
            switch(obj.Material)
                case 'Carbon Steel'
                    Fm = 1.0;
                case 'Stainless steel clad'
                    Fm = 2.5;
                case 'Stainless steel'
                    Fm = 4.0;
                case 'Nickel alloy clad'
                    Fm = 4.5;
                case 'Nickel alloy'
                    Fm = 9.8;
                case 'Titanium clad'
                    Fm = 4.9;
                case 'Titanium'
                    Fm = 10.6;
            end
        end
        %----------------------------------------
        function value = get.Cbm(obj)
            % Cbm (Bare Module Cost)
            B1 = 2.50;
            B2 = 1.72;
            [Fm,Fp] = obj.calcFactors;
            value = obj.Cp*(B1 + B2*Fm*Fp)*(obj.CEPCI_2014/obj.CEPCI_1996);
        end
        %----------------------------------------
        function value = get.Cbm0(obj)
            B1 = 2.50;
            B2 = 1.72;
            value= obj.Cp*(B1 +B2*1.0*1.0)*(obj.CEPCI_2014/obj.CEPCI_1996);
        end
        %----------------------------------------
        function value = get.Ctm(obj)
            % Ctm (Total Module Cost)
            value = 1.18*obj.Cbm;
        end
        %----------------------------------------
        function value = get.Cgr(obj)
            % Cgr (Grass root Cost)
            value = obj.Ctm + 0.5*obj.Cbm0;
        end
        %----------------------------------------
        function value = get.TotalCost(obj)
            value = obj.Cgr + obj.Tray.Cgr;
        end
        %----------------------------------------
        function value = get.StreamProfit(obj)
            value = (obj.Distillate.Flow*obj.Distillate.Cost ...
                + obj.Bottoms.Flow*obj.Bottoms.Cost ...
                - obj.Feed.Flow*obj.Feed.Cost)*24*365;
        end
        %----------------------------------------
        function value = get.ProfitPBP1(obj)
            value = obj.StreamProfit ...
                - obj.TotalCost;
        end
    end
end