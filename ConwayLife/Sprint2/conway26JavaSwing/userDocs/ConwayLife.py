#!/usr/bin/env python3
"""
Conway's Game of Life - griglia 10x10
Esempio semplice che stampa la griglia in console ad ogni iterazione.

- Cellule vive: 'O'
- Cellule morte: '.'
- Boundary: bordo fisso (celle oltre il bordo sono considerate morte).
- Modalità:
    * random initialization
    * preset (glider) - commentato nel codice
- Esecuzione:
    * modalità automatica (delay configurabile)
    * modalità passo-passo (premi Invio per avanzare)
"""

import random
import time
import os
import sys
from copy import deepcopy

ROWS = 10
COLS = 10

def clear_screen():
    """Pulisce lo schermo della console in modo cross-platform."""
    if os.name == 'nt':
        os.system('cls')
    else:
        os.system('clear')

def make_empty_grid(rows=ROWS, cols=COLS):
    """Crea una griglia (lista di liste) inizialmente vuota (tutte le celle morte)."""
    return [[0 for _ in range(cols)] for _ in range(rows)]

def random_grid(rows=ROWS, cols=COLS, prob_alive=0.25):
    """Crea una griglia iniziale casuale. prob_alive = probabilità che una cella sia viva."""
    return [[1 if random.random() < prob_alive else 0 for _ in range(cols)] for _ in range(rows)]

def glider_pattern():
    """Restituisce una griglia con un glider come esempio (posizionato in alto a sinistra)."""
    g = make_empty_grid()
    # pattern 'glider'
    g[1][2] = 1
    g[2][3] = 1
    g[3][1] = 1
    g[3][2] = 1
    g[3][3] = 1
    return g

def print_grid(grid, generation):
    """Stampa la griglia su console con una intestazione che mostra il numero di generazione."""
    clear_screen()
    print(f"Conway's Game of Life — Griglia {ROWS}x{COLS} — Generazione {generation}")
    print('-' * (COLS + 6))
    for row in grid:
        line = ''.join('O' if cell else '.' for cell in row)
        print('| ' + line + ' |')
    print('-' * (COLS + 6))

def count_alive_neighbors(grid, r, c):
    """Conta celle vive intorno alla cella (r, c).
       Qui consideriamo **bordo fisso**: celle fuori dai limiti sono morte."""
    rows = len(grid)
    cols = len(grid[0])
    cnt = 0
    for dr in (-1, 0, 1):
        for dc in (-1, 0, 1):
            if dr == 0 and dc == 0:
                continue
            nr, nc = r + dr, c + dc
            if 0 <= nr < rows and 0 <= nc < cols:
                cnt += grid[nr][nc]
    return cnt

def next_generation(grid):
    """Calcola la generazione successiva secondo le regole di Conway."""
    rows = len(grid)
    cols = len(grid[0])
    new_grid = make_empty_grid(rows, cols)
    for r in range(rows):
        for c in range(cols):
            alive = grid[r][c] == 1
            neighbors = count_alive_neighbors(grid, r, c)
            # Regole:
            # - Una cella viva con 2 o 3 vicini vivi sopravvive.
            # - Una cella morta con esattamente 3 vicini vivi diventa viva.
            # - Altrimenti la cella è (o diventa) morta.
            if alive and (neighbors == 2 or neighbors == 3):
                new_grid[r][c] = 1
            elif not alive and neighbors == 3:
                new_grid[r][c] = 1
            else:
                new_grid[r][c] = 0
    return new_grid

def run_simulation(initial_grid, steps=None, delay=0.8, step_by_step=False):
    """
    Esegue la simulazione.
    - initial_grid: griglia iniziale (lista di liste di 0/1)
    - steps: numero massimo di iterazioni (None -> infinito fino a interruzione)
    - delay: ritardo (s) tra generazioni in modalità automatica
    - step_by_step: se True, aspetta che l'utente prema Invio per avanzare
    """
    grid = deepcopy(initial_grid)
    gen = 0
    seen_states = set()  # opzionale: rilevamento cicli brevi (via stringificazione)
    try:
        while True:
            print_grid(grid, gen)
            # opzione di terminazione se si vuole limitare le iterazioni
            if steps is not None and gen >= steps:
                print("Limite di passi raggiunto. Fine simulazione.")
                break

            # rileva stato stabile o vuoto (tutte le celle morte): termina
            flat = ''.join('1' if cell else '0' for row in grid for cell in row)
            if flat.count('1') == 0:
                print("Tutte le celle sono morte. Fine simulazione.")
                break
            if flat in seen_states:
                print("Stato già visto (ciclo/stabilizzazione). Fine simulazione.")
                break
            seen_states.add(flat)

            # avanzamento
            if step_by_step:
                input("Premi Invio per la prossima generazione (Ctrl+C per uscire)...")
            else:
                time.sleep(delay)

            grid = next_generation(grid)
            gen += 1

    except KeyboardInterrupt:
        print("\nSimulazione interrotta dall'utente.")

def choose_initial_grid():
    """Interfaccia semplice per scegliere la griglia iniziale."""
    print("Scegli inizializzazione della griglia 10x10:")
    print("1) Random (probabilità 25%)")
    print("2) Glider (esempio statico)")
    print("3) Inserimento manuale (inserisci coordinate) ")
    choice = input("Scelta [1-3] (default 1): ").strip() or '1'
    if choice == '2':
        return glider_pattern()
    elif choice == '3':
        g = make_empty_grid()
        print("Inserisci coppie di coordinate riga,col (0-based). Una per riga. Vuoto per terminare.")
        while True:
            line = input("Coord (es. 2,3): ").strip()
            if not line:
                break
            try:
                parts = line.split(',')
                r = int(parts[0].strip())
                c = int(parts[1].strip())
                if 0 <= r < ROWS and 0 <= c < COLS:
                    g[r][c] = 1
                else:
                    print("Coordinate fuori range.")
            except Exception:
                print("Formato non valido.")
        return g
    else:
        return random_grid()

def main():
    print("Conway's Game of Life - griglia 10x10\n")
    grid = choose_initial_grid()
    mode = input("Esecuzione: (a)utomatica o (s)tep-by-step? [a/s] (default a): ").strip().lower() or 'a'
    if mode.startswith('s'):
        step_by_step = True
        delay = None
    else:
        step_by_step = False
        d = input("Delay tra le generazioni in secondi (es. 0.8) [default 0.8]: ").strip()
        try:
            delay = float(d) if d else 0.8
        except Exception:
            delay = 0.8

    steps_input = input("Numero massimo di passi (vuoto = infinito fino a stabilizzazione): ").strip()
    steps = int(steps_input) if steps_input.isdigit() else None

    run_simulation(grid, steps=steps, delay=delay or 0.8, step_by_step=step_by_step)

if __name__ == "__main__":
    main()



"""
Note e suggerimenti

Il codice usa bordo fisso (celle fuori dalla griglia sono considerate morte). Se preferisci una griglia toroidale (wrap-around), posso modificare count_alive_neighbors per considerare i bordi come connessi.

Ho incluso un preset glider come esempio. Puoi anche inserire manualmente le coordinate iniziali.

Se vuoi, posso fornirti:

la versione con griglia toroidale (wrap-around),

una versione grafica (Tkinter) con animazione,

una versione che salva ogni frame su immagine PNG,

o la stessa implementazione in altro linguaggio (Java/Kotlin/C++).

Vuoi che la modifichi per usare bordi toroidali o che generi la versione grafica con Tkinter?
"""






