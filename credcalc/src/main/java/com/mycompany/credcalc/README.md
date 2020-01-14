# CreditCalc
учебное задание по разработке кредитного калькулятора

Задача «Кредитный калькулятор»

Написать программу, рассчитывающую график платежей по кредиту.
Входные данные

Текстовый файл:

id клиента;	размер кредита;	процент по кредиту;	размер первоначального взноса;	вид платежа (аннуитетный А или дифференцированный Д);	Срок кредита, в месяцях;	Срок первого платежа;

Постановка задачи

При заданных данных в строке CSV файла  программа должна вывести таблицу рассчета погашения кредита.
Предполагается, что выплаты могут производится аннуитетными (ежемесячная сумма выплаты не меняется, но с каждым месяцем в этой сумме содержится больше выплат основного долга по кредиту и меньше процентных выплат) или дифференцированными (общая сумма основного долга делится на равные части пропорционально сроку кредитования. Ежемесячно в течение всего срока погашения кредита заемщик выплачивает банку часть основного долга плюс начисленные на его остаток проценты. Основной долг из месяца в месяц уменьшается) платежами.

Вычисление суммы аннуитетного платежа производится по формуле:

Payment = S*(p+(p/((1+N)^N-1))
, где p – это нормализованная месячная процентная ставка (p = P / 100 / 12).

Для S = 1200000, P = 17 и N = 36 сумма аннуитетного платежа равна 42783.27.

Для вычисления процентной составляющей в платеже можно воспользоваться следующей формулой: Percent = p * Rem, где Rem – остаток задолженности по кредиту. Тогда сумма, идущая в погашение основного долга, равна Principal = Payment - Percent.

Схема оплаты по дифференцированному платежу выглядит следующим образом:
Условие: сумма кредита — 300 000 рублей, срок кредита — 6 месяцев, ставка по кредиту — 20%. Погашение кредита осуществляется дифференцированными платежами:
1. Ежемесячный платеж по основному долгу = сумма кредита / количество платежных периодов в течение всего срока кредита.
300 000 рублей / 6 месяцев = 50 000 рублей.
2. Ежемесячная сумма начисленных процентов по кредиту = остаток основного долга в текущем периоде * годовая процентная ставка * число дней в платежном периоде (от 28 до 31) / число дней в году (365 или 366).
1-й месяц = 300 000 рублей*20%*31/365=5 095,89 рубля.
2-й месяц = 250 000 рублей*20%*31/365=4 246,58 рубля.
3-й месяц = 200 000 рублей*20%*30/365=3 287,67 рубля.
4-й месяц = 150 000 рублей*20%*31/365=2547,95 рубля.
5-й месяц = 100 000 рублей*20%*30/365=1643,84 рубля.
6-й месяц = 50 000 рублей*20%*31/365=849,32 рубля.
3. Ежемесячный платеж по кредиту = ежемесячный платеж по основному долгу + ежемесячная сумма начисленных процентов по кредиту.
1-й месяц = 50 000 рублей+5 095,89 рубля=55 095,89 рубля.
2-й месяц = 50 000 рублей+4 246,58 рубля=54 246,58 рубля.
3-й месяц = 50 000 рублей+3 287,67 рубля=53 287,67 рубля.
4-й месяц = 50 000 рублей+2 547,95 рубля=52 547,95 рубля.
5-й месяц = 50 000 рублей+1 643,84 рубля=51 643,84 рубля.
6-й месяц = 50 000 рублей+849,32 рубля=50 849,32 рубля.
Итого платежи по кредиту составили 317 671,25 рубля.


Кроме показа таблицы программа должна вычислить и отобразить общую сумму переплаты (суммарное количество выплаченных процентов).
Выходные данные

Текстовый файл

id заемщика
Дата            Сумма к оплате          Проценты        Основной долг
...
15.09.2014          42783.27             2340.90             40442.37
15.10.2014          42783.27             1767.96            41015.31
...


