clear;
x2 = linspace(0,1,100);
xx = 0:0.05:1;
xtest = [0.11, 0.22, 0.33, 0.44, 0.55, 0.66, 0.77, 0.88, 0.99];
figure;

% VENTURE 1 DATA PTS VS SPLINE INTERPOLATING POLYNOMIAL FOR 1001 PTS
% BETWEEN 0 AND 1
hAx = subplothandles(2,4,14);
bed=BED('Venture 1'); 
fv1_pchip = fit(bed.X',bed.Y','pchip');
fv1_poly6 = fit(bed.X',bed.Y','poly6');
fv1_cubic = fit(bed.X',bed.Y','cubicinterp');


plot(hAx(1), bed.X,bed.Y,'ko',x2, fv1_pchip(x2),'k--','LineWidth',1);
plot(hAx(1), xx, fv1_poly6(xx),'k+');%,'MarkerSize',10);
plot(hAx(1), xx, fv1_cubic(xx),'k*');
plot(hAx(1), 0:1, 0:1, '-k');
title(hAx(1),'(a)');
legend(hAx(1),{'data','pchip','poly6','cspline'},'location','southeast');
hAx(1).YLim = [0 1];
hAx(1).XLabel.String = 'x (mole frac.)';
hAx(1).YLabel.String = 'y (mole frac.)';


% VENTURE 2 DATA PTS VS SPLINE INTERPOLATING POLYNOMIAL FOR 1001 PTS
% BETWEEN 0 AND 1
% subplot(2,2,2);
bed=BED('Venture 2'); 
fv2_pchip = fit(bed.X',bed.Y','pchip');
fv2_poly6 = fit(bed.X',bed.Y','poly6');
fv2_cubic = fit(bed.X',bed.Y','cubicinterp');


plot(hAx(2), bed.X,bed.Y,'ko',x2, fv2_pchip(x2),'k--','LineWidth',1);
plot(hAx(2), xx, fv2_poly6(xx),'k+');%,'MarkerSize',10);
plot(hAx(2), xx, fv2_cubic(xx),'k*');
plot(hAx(2), 0:1, 0:1, '-k');
title(hAx(2),'(b)');
legend(hAx(2),{'data','pchip','poly6','cspline'},'location','southeast');
hAx(2).XLim = [0 1];
hAx(2).XLabel.String = 'x (mole frac.)';
hAx(2).YLabel.String = 'y (mole frac.)';

% VENTURE 3 DATA PTS VS SPLINE INTERPOLATING POLYNOMIAL FOR 1001 PTS
% BETWEEN 0 AND 1
% subplot(2,2,3);
bed=BED('Venture 3'); 
fv3_pchip = fit(bed.X',bed.Y','pchip');
fv3_poly6 = fit(bed.X',bed.Y','poly6');
fv3_cubic = fit(bed.X',bed.Y','cubicinterp');


plot(hAx(3), bed.X,bed.Y,'ko', x2, fv3_pchip(x2),'k--','LineWidth',1);
plot(hAx(3), xx, fv3_poly6(xx),'k+');
plot(hAx(3), xx, fv3_cubic(xx),'k*');
plot(hAx(3), 0:1, 0:1, '-k');
title(hAx(3),'(c)');
legend(hAx(3),{'data','pchip','poly6','cspline'},'location','southeast');
hAx(3).YLim = [0 1];
hAx(3).XLabel.String = 'x (mole frac.)';
hAx(3).YLabel.String = 'y (mole frac.)';

plot(hAx(4), bed.X,bed.Y,'ko', x2, fv3_pchip(x2),'k--','LineWidth',1);
plot(hAx(4), xx, fv3_poly6(xx),'k+');%,'MarkerSize',10);
plot(hAx(4), xx, fv3_cubic(xx),'k*');
plot(hAx(4), 0:1, 0:1, '-k');
title(hAx(4),'(d)');
legend(hAx(4),{'data','pchip','poly6','cspline'},'location','southeast');
ylim([0.60 .82]);
xlim([0.58 .78]);
hAx(4).XLabel.String = 'x (mole frac.)';
hAx(4).YLabel.String = 'y (mole frac.)';
