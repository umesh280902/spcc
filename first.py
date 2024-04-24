from typing import List, Dict, Set

# Define types
Grammar = Dict[str, List[str]]
Productions = List[List[str]]

# Define grammar and productions
grammar: Grammar = {
    "E": ["TR"],
    "R": ["+TR", "#"],
    "T": ["FY", "#"],
    "Y": ["*FY", "#"],
    "F": ["i", "(E)"]
}
productions: Productions = list(grammar.values())

# Extract terminals
terminals: Set[str] = set()
for p in productions:
    for production in p:
        for c in production:
            if not c.isupper():
                terminals.add(c)

print("Terminals:", terminals)
print("Productions:", productions)
print("Non-terminals:", list(grammar.keys()))


def extract_first(non_terminals: Set[str], productions: Productions) -> Dict[str, Set[str]]:
    first: Dict[str, Set[str]] = {}

    # Initialize first sets for terminals
    for terminal in terminals:
        first[terminal] = {terminal}

    # Initialize first sets for non-terminals
    for non_terminal in non_terminals:
        first[non_terminal] = set()

    # Compute first sets
    changed = True
    while changed:
        changed = False
        for non_terminal in non_terminals:
            for production in grammar[non_terminal]:
                for c in production:
                    if c.isupper():
                        f = first.get(c)
                        if "#" not in f:
                            first[non_terminal].update(f)
                            changed = True
                            break
                        else:
                            if c == production[-1]:
                                first[non_terminal].update(f)
                            else:
                                f.discard("#")
                                first[non_terminal].update(f)
                    else:
                        first[non_terminal].add(c)
                        break

    return first


# Extract first sets
first_sets = extract_first(set(grammar.keys()), productions)
print("First sets:", first_sets)
