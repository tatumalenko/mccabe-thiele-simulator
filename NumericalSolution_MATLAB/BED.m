classdef BED
    properties
        X
        Y
    end
    methods
        function obj = BED(str)
            if strcmp(str,'Fancy Scarves') || strcmp(str,'Venture 1')
                obj.X = [0 0.013 0.025 0.052 0.09 0.16 0.25 0.37 0.52 0.7 0.85 0.96 1];
                obj.Y = [0 0.07 0.13 0.22 0.33 0.47 0.59 0.69 0.78 0.86 0.93 0.97 1];
            elseif strcmp(str,'Fancy Ties')
                obj.X = 1 - [0 0.013 0.025 0.052 0.09 0.16 0.25 0.37 0.52 0.7 0.85 0.96 1];
                obj.Y = 1 - [0 0.07 0.13 0.22 0.33 0.47 0.59 0.69 0.78 0.86 0.93 0.97 1];
            elseif strcmp(str,'Cocoa') || strcmp(str,'Venture 2')
                obj.X = [0 0.1 0.25 0.37 0.42 0.55 0.68 0.71 0.82 0.9 0.94 0.96 1];
                obj.Y = [0 0.2 0.36 0.53 0.62 0.74 0.84 0.86 0.91 0.96 0.97 0.98 1];
            elseif strcmp(str,'Sugar')
                obj.X = 1 - [0 0.1 0.25 0.37 0.42 0.55 0.68 0.71 0.82 0.9 0.94 0.96 1];
                obj.Y = 1 - [0 0.2 0.36 0.53 0.62 0.74 0.84 0.86 0.91 0.96 0.97 0.98 1];
            elseif strcmp(str,'Watches') || strcmp(str,'Venture 3')
                obj.X = [0 0.09 0.15 0.26 0.37 0.41 0.59 0.64 0.78 0.82 0.91 0.96 1];
                obj.Y = [0 0.38 0.5 0.55 0.57 0.6 0.7 0.72 0.8 0.85 0.91 0.96 1];
            elseif strcmp(str,'iPads')
                obj.X = 1 - [0 0.09 0.15 0.26 0.37 0.41 0.59 0.64 0.78 0.82 0.91 0.96 1];
                obj.Y = 1 - [0 0.38 0.5 0.55 0.57 0.6 0.7 0.72 0.8 0.85 0.91 0.96 1];
            end
        end
        function handles = Plot(obj)
            hFig = figure;
            hAx  = axes('Parent',hFig);
            hLine = plot(hAx,obj.X,obj.Y,'o');
            hold on
            plot(hAx,[0 1],[0 1],'k');
            hold off
            handles.Figure = hFig;
            handles.Axes   = hAx;
            handles.Line   = hLine;
        end
        function sgof = fitPoly(obj)
            figure;
            [poly4,gof4] = fit(obj.X',obj.Y','poly4');
            [poly5,gof5] = fit(obj.X',obj.Y','poly5');
            [poly6,gof6] = fit(obj.X',obj.Y','poly6');
            [poly7,gof7] = fit(obj.X',obj.Y','poly7');
            [poly8,gof8] = fit(obj.X',obj.Y','poly8');
            [poly9,gof9] = fit(obj.X',obj.Y','poly9');
            sgof.rsquare4 = gof4.rsquare;
            sgof.rsquare5 = gof5.rsquare;
            sgof.rsquare6 = gof6.rsquare;
            sgof.rsquare7 = gof7.rsquare;
            sgof.rsquare8 = gof8.rsquare;
            sgof.rsquare9 = gof9.rsquare;
            
            plot(poly4,obj.X,obj.Y);
            hold on;
            plot(poly5,obj.X',obj.Y');
            plot(poly6,obj.X',obj.Y');
            plot(poly7,obj.X',obj.Y');
            plot(poly8,obj.X',obj.Y');
            plot(poly9,obj.X',obj.Y');
            hold off;
            
            
        end
    end
            
    
end