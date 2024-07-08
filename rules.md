## Allgemein
- Jeder Spieler erhält eine Armee, die aus einzelnen Einheiten besteht (Die Anzahl der Einheiten pro Karte soll vorgegeben sein aber max. 50 pro Armee). Eine Einheit hat zu Beginn 10 TP (Trefferpunkte)
- Pro Runde erteilt jeder Spieler seinen Truppen Befehle. Sind alle Befehle von jedem Spieler ausgeführt, ist ein Tag vergangen
- Sinken die TP einer Einheit auf 0, soll sie automatisch vom Feld verschwinden
- Das einzige Ziel ist es, alle gegnerische Einheiten zu besiegen und den Gegner so in die Flucht zu schlagen

## Befehlsmenü
### Marschbefehl
- Nach Auswählen der zu bewegenden Einheit soll ihr Bewegungsradius eingeblendet werden 
- Dieser soll sichtbar machen auf welche Felder sich eine Einheit bewegen kann 
- Der Spieler wählt dementsprechend ein passendes Feld aus 
- Sobald die einheit am Feld angekommen ist, soll sie bis zum Beginn der nächsten Runden ausgegraut werden
### Feuerbefehl
- Der Feuerbefehl soll sich freischalten, sobald eine Einheit in der Position ist eine gegnerische Einheit anzugreifen
- Falls mehrere gegnerische Einheiten angreifbar sind, soll eine Auswahlmöglichkeit gegeben werden
- Der Spieler hat die wahl zwischen Nah und Fernkampfwaffen: Nahkampfwaffen können direkt abgefeuert werden, jedoch kein der Gegner auch direkt zurückschießen. Fernkampfwaffen können erst nach einer weiteren Runde abgefeuert werden, jedoch kamm der Gegner auch erst eine Runde später zurückschießen
### EINEN-Befehl
- Zwei oder mehr Truppen der gleichen Art können sich vereinen, indem sie vom Spieler aufeinander gezogen werden
- Die TP der Truppen werden dabei addiert

## Gefechtsmenü
### Einheit
- Hier sollen Angaben über Truppen ("Typ" und "TP") während des Gefechts aufgelistet werden
### Info
Besteht aus den Unterpunkten:
- Status: Hier sollen Informationen über die Kampf-Info, Name des Schlachtfelds, Anzahl der Tage etc. aufgelistet werden
- Regeln: Hier sollen Informationen über die ausgewählten Regeln (siehe "Regeln") aufgelistet werden
### Sichern
- Hier soll dem Spieler die Möglichkeit gegeben werden das Spiel zu speichern
### Ende
- Hier kann der Spieler eine Runde (Tag) beenden, nachdem er alle gewünschten Befehle an seine Truppen erteilt hat

## Einheiten
|                     | Weapon 1                | Weapon 2  | Fire | Vision | Move | Ground Unit | Air Unit |
|---------------------|-------------------------|-----------|------|--------|------|-------------|----------|
| Infantry            | None                    | Mach. Gun | 1    | 2      | 3    | X           |          |
| Mechanized Infantry | Bazooka                 | Mach. Gun | 1    | 2      | 2    | X           |          |
| Tank                | Tank Cannon             | Mach. Gun | 1    | 3      | 6    | X           |          |
| Mobile Artillery    | Cannon                  | None      | 2-3  | 1      | 5    | X           |          |
| Anti Air            | Vulcan Cannon           | None      | 1    | 2      | 6    | X           |          |
| Fighter             | Missiles                | None      | 1    | 2      | 9    |             | X        |
| Bomber              | Bombs                   | None      | 1    | 2      | 7    |             | X        |
| Battle Copter       | Air-to-Surface Missiles | Mach. Gun | 1    | 3      | 6    |             | X        |

## Weg-Kosten
| UNIT TYPE           | Plains | Rivers | Mtns. | Woods | Roads | Cities | Seas | HQ | Airports | Ports | Bridges | Shoals | Bases | Reefs |
|---------------------|--------|--------|-------|-------|-------|--------|------|----|----------|-------|---------|--------|-------|-------|
| Infantry            | 1      | 2      | 2     | 1     | 1     | 1      | 1    | 1  | 1        | 1     | 1       | 1      | 1     | 1     |
| Mechanized Infantry | 1      | 1      | 1     | 1     | 1     | 1      | 1    | 1  | 1        | 1     | 1       | 1      | 1     | 1     |
| Tank                | 1      | 1      | 2     | 1     | 2     | 1      | 1    | 1  | 1        | 1     | 1       | 1      | 1     | 1     |
| Mobile Artillery    | 1      | 1      | 2     | 1     | 1     | 1      | 1    | 1  | 1        | 1     | 1       | 1      | 1     | 1     |
| Anti-Air            | 1      | 1      | 2     | 1     | 1     | 1      | 1    | 1  | 1        | 1     | 1       | 1      | 1     | 1     |
| Fighter             | 1      | 1      | 1     | 1     | 1     | 1      | 1    | 1  | 1        | 1     | 1       | 1      | 1     | 1     |
| Bomber              | 1      | 1      | 1     | 1     | 1     | 1      | 1    | 1  | 1        | 1     | 1       | 1      | 1     | 1     |
| Battle-Copter       | 1      | 1      | 1     | 1     | 1     | 1      | 1    | 1  | 1        | 1     | 1       | 1      | 1     | 1     |
