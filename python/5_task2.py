def star2():
    with open("../day5_data.txt") as fp:
        lines = fp.readlines()
        line = [int(num) for num in lines[0].strip("seeds: ").split()]
        seedRanges = [(line[i], line[i + 1]) for i in range(0, len(line), 2)]

        maps = [[]]
        i = 3
        mapNum = 0
        while i < len(lines):
            if lines[i] == "\n":
                i += 2
                mapNum += 1
                maps.append([])
            else:
                maps[mapNum].append([int(num) for num in lines[i].strip("\n").split()])
                i += 1

        for category in maps:
            new_seedRanges = []
            for seedRange in seedRanges:
                seed_start, seed_len = seedRange
                seed_end = seed_start + seed_len

                for map_range in category:
                    dest_start, src_start, range_len = map_range
                    src_end = src_start + range_len

                    # Check for partial or full overlap
                    if seed_start < src_end and seed_end > src_start:
                        if seed_start < src_start:
                            # Part of the range before the mapping range
                            new_seedRanges.append((seed_start, src_start - seed_start))

                        overlap_start = max(seed_start, src_start)
                        overlap_end = min(seed_end, src_end)
                        offset = dest_start - src_start
                        new_start = overlap_start + offset
                        new_len = overlap_end - overlap_start
                        new_seedRanges.append((new_start, new_len))

                        if seed_end > src_end:
                            # Part of the range after the mapping range
                            seed_start = src_end
                            seed_len = seed_end - src_end
                        else:
                            break

                if seed_start < seed_end:
                    new_seedRanges.append((seed_start, seed_len))

            seedRanges = new_seedRanges

        min_location = min(seedRange[0] for seedRange in seedRanges)
        return min_location

print(star2())