function main
	j = java.text.NumberFormat.getCurrencyInstance();
    hAx = subplothandles(3,3,14);
    col1 = calcVentureResults(1); 
    col2 = calcVentureResults(2);
    col3 = calcVentureResults(3);
    col1.PlotStages(hAx(1)); title(hAx(1),['Venture: 1/#Trays: ' ...
        num2str(col1.NumberOfTrays)  '/$PBP1y: ' ...
        char(j.format(col1.ProfitPBP1))]);
    col2.PlotStages(hAx(2)); title(hAx(2),['Venture: 2/#Trays: ' ...
        num2str(col2.NumberOfTrays)  '/$PBP1y: ' ...
        char(j.format(col2.ProfitPBP1))]);
    col3.PlotStages(hAx(3)); title(hAx(3),['Venture: 3/#Trays: ' ...
        num2str(col3.NumberOfTrays)  '/$PBP1y: ' ...
        char(j.format(col3.ProfitPBP1))]);  
    
end

function column = calcVentureResults(vnum)
    column = Column; % Instantiate Column object
    % Venture 1
    if vnum == 1
        Components = {'Fancy Scarves','Fancy Ties'};
        column.Diameter         = 2;
        column.Length           = 10;
        column.PressureGauge    = 16.4;
        column.RefluxRatio      = 3.5;
        column.Material         = 'Titanium';
        column.Tray.Material    = 'Nickel alloy';
        column.Tray.Diameter    = column.Diameter;
        column.Feed.Temperature             = 421.8;            % K
        column.Feed.Flow                    = 100;              % kmol/h
        column.Feed.ComponentName           = Components;
        column.Feed.ComponentFrac           = [0.5 0.5];        % mol/mol
        column.Feed.Cost                    = 25;               % CDN$/kmol

        column.Distillate.ComponentName     = Components;
        column.Distillate.ComponentFrac     = [0.95 0.05];      % mol/mol
        column.Distillate.Cost              = 100;              % CDN$/kmol

        column.Bottoms.ComponentName        = Components;
        column.Bottoms.ComponentFrac        = [0.1 0.9];        % mol/mol
        column.Bottoms.Cost                 = 30;               % CDN$/kmol
    % Venture 2
    elseif vnum == 2
        Components = {'Cocoa','Sugar'};
        column.Diameter         = 2.4;
        column.Length           = 22;
        column.PressureGauge    = 6.5;
        column.RefluxRatio      = 10.6;
        column.Material         = 'Nickel alloy clad';
        column.Tray.Material    = 'Nickel alloy';
        column.Tray.Diameter    = column.Diameter;

        column.Feed.Temperature             = 399;              % K
        column.Feed.Flow                    = 100;              % kmol/h
        column.Feed.ComponentName           = Components;
        column.Feed.ComponentFrac           = [0.25 0.75];      % mol/mol
        column.Feed.Cost                    = 42;               % CDN$/kmol

        column.Distillate.ComponentName     = Components;
        column.Distillate.ComponentFrac     = [0.72 0.28];      % mol/mol
        column.Distillate.Cost              = 200;              % CDN$/kmol

        column.Bottoms.ComponentName        = Components;
        column.Bottoms.ComponentFrac        = [0.15 0.85];      % mol/mol
        column.Bottoms.Cost                 = 47;               % CDN$/kmol
    % Venture 3
    elseif vnum == 3
        Components = {'Watches','iPads'};
        column.Diameter         = 1.5;
        column.Length           = 32;
        column.PressureGauge    = 2.1;
        column.RefluxRatio      = 3.8;
        column.Material         = 'Stainless steel clad';
        column.Tray.Material    = 'Stainless steel';
        column.Tray.Diameter    = column.Diameter;

        column.Feed.Temperature             = 509;              % K
        column.Feed.Flow                    = 100;              % kmol/h
        column.Feed.ComponentName           = Components;
        column.Feed.ComponentFrac           = [0.45 0.55];      % mol/mol
        column.Feed.Cost                    = 206;              % CDN$/kmol

        column.Distillate.ComponentName     = Components;
        column.Distillate.ComponentFrac     = [0.85 0.15];      % mol/mol
        column.Distillate.Cost              = 375;              % CDN$/kmol

        column.Bottoms.ComponentName        = Components;
        column.Bottoms.ComponentFrac        = [0.11 0.89];      % mol/mol
        column.Bottoms.Cost                 = 50;               % CDN$/kmol
    end

    column.Bottoms.Flow = column.Feed.Flow/(1 ...
        + (column.Bottoms.ComponentFrac(1) ...
        - column.Feed.ComponentFrac(1)) ...
        /(column.Feed.ComponentFrac(1) ...
        - column.Distillate.ComponentFrac(1))); % kmol/h
    column.Distillate.Flow = column.Feed.Flow ...
        - column.Feed.Flow/(1 + (column.Bottoms.ComponentFrac(1) ...
        - column.Feed.ComponentFrac(1))...
        /(column.Feed.ComponentFrac(1) ...
        - column.Distillate.ComponentFrac(1))); % kmol/h;

    numberoftrays = column.NumberOfTrays;
    column.Tray.NumberOfTrays = numberoftrays;

    disp(' ');
    disp('----------------------------------------');
    disp('                RESULTS');
    disp('----------------------------------------');
    disp(['           Venture: ' num2str(vnum)]);
    disp(['   Number of Trays: ' num2str(column.NumberOfTrays)]);

    disp(' ');
    disp('********** Column Flow Rates ***********');
    disp(['         Feed Flow: ' num2str(column.Feed.Flow)]);
    disp(['   Distillate Flow: ' num2str(column.Distillate.Flow)]);
    disp(['      Bottoms Flow: ' num2str(column.Bottoms.Flow)]);

    disp(' ');
    disp('*********** Column Costing *************');
    disp(['      Capital Cost: ' num2str(column.Cp)]);
    disp(['  Bare Module Cost: ' num2str(column.Cbm)]);
    disp([' Total Module Cost: ' num2str(column.Ctm)]);
    disp(['  Grass Roots Cost: ' num2str(column.Cgr)]);

    disp(' ');
    disp('************* Tray Costing ************');
    disp(['      Capital Cost: ' num2str(column.Tray.Cp)]);
    disp(['  Bare Module Cost: ' num2str(column.Tray.Cbm)]);
    disp([' Total Module Cost: ' num2str(column.Tray.Ctm)]);
    disp(['  Grass Roots Cost: ' num2str(column.Tray.Cgr)]);

    disp(' ');
    disp('**** Both Column and Tray Costing ****');
    disp(['      Capital Cost: ' num2str(column.Cp +column.Tray.Cp)]);
    disp(['  Bare Module Cost: ' num2str(column.Cbm+column.Tray.Cbm)]);
    disp([' Total Module Cost: ' num2str(column.Ctm+column.Tray.Ctm)]);
    disp(['  Grass Roots Cost: ' num2str(column.Cgr+column.Tray.Cgr)]);

    disp(' ');
    disp('****** Overall Revenues/Profit *******');
    disp(['Production Revenue: ' num2str(column.StreamProfit)]);
    disp([' 1 Year PBP Profit: ' num2str(column.ProfitPBP1)]);
end