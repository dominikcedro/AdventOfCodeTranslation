from collections import Counter


def read_input(puzzle):
    with open("../day7_data.txt") as f:
        data = f.read().splitlines()
    return data


data = read_input('7')

pairs_dict = {}
for i in data:
    pairs_dict[i.split(' ')[0]] = i.split(' ')[1]

### Part 2 ###

poker_hands = pairs_dict.keys()

# Create a dictionary to store the poker hands
hand_types = {"Five of a kind": [],  # example: 55555
              "Four of a kind": [],  # example: 5555A
              "Full house": [],  # example: 555AA
              "Three of a kind": [],  # example: 555AK
              "Two pair": [],  # example: 55AAK
              "One pair": [],  # example: 55AKQ
              "High card": []}  # example: 5AKQJ


def assign_type(length, hand, highest_card_score):
    # correct this function -> the comparison should be based on length only
    if length == 5:
        hand_types.setdefault("High card", []).append(hand)
    elif length == 4:
        hand_types.setdefault("One pair", []).append(hand)
    elif length == 3:
        # this can be either a two of a kind or three of a kind
        if highest_card_score == 3:
            hand_types.setdefault("Three of a kind", []).append(hand)
        else:
            hand_types.setdefault("Two pair", []).append(hand)
    elif length == 2:
        # this can be either a full house or four of a kind
        if highest_card_score == 3:
            hand_types.setdefault("Full house", []).append(hand)
        else:
            hand_types.setdefault("Four of a kind", []).append(hand)
    else:
        # this is a five of a kind
        hand_types.setdefault("Five of a kind", []).append(hand)


for hand in poker_hands:
    # Count the occurrences of each element in the string
    card_counts = Counter(hand)

    # Sort the cards by count and then by card value
    sorted_cards = sorted(card_counts.items(), key=lambda x: (x[1], x[0]), reverse=True)
    highest_card_score = sorted_cards[0][1]
    length = len(sorted_cards)

    if ('J' not in hand):  # example case: JJ532 -> it should still count as a one pair
        pass

    else:
        # update length (reduce by 1) if the hand contains a J
        length = max(1, length - 1)  # the max condition accounts for the case 'JJJJJ'
        # update the high card score by the number of Js in the hand
        if sorted_cards[0][0] != 'J':
            highest_card_score += hand.count('J')
        else:
            # highest card score is J + the next highest card score
            try:
                highest_card_score = highest_card_score + sorted_cards[1][1]
            # if the next highest card does not exist -> account for the case 'JJJJJ'
            except IndexError:
                pass

    assign_type(length, hand, highest_card_score)

# changed the strength to place J at the end
strength = "AKQT98765432J"
for key, value in hand_types.items():
    # sort based on the first card in the hand, if cards are equal, compare the second card, etc.
    hand_types[key] = sorted(value, key=lambda x: [strength.index(card[0]) for card in x])

# add all values to a list
final_list = []
for key, value in hand_types.items():
    final_list += value
# reverse the list so that the highest value is first
final_list.reverse()

# multiply each pair by its index in the list
total = 0
for i, pair in enumerate(final_list):
    total += (i + 1) * int(pairs_dict[pair])

print("Answer to Part 2:", total)