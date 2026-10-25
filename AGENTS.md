# Repository Guidance

## Purpose

This repository is a compact teaching portfolio artifact. It should demonstrate
clear Java, correct graph algorithms, thoughtful tests, and usable instructional
materials. It is not a production graph framework and must not reproduce a
Bowdoin assignment or solution.

## Technical boundaries

- Target Java 21 and build with the checked-in Maven wrapper.
- Prefer readable standard-library implementations over clever abstractions.
- Preserve deterministic traversal order by using insertion-ordered collections.
- State algorithm preconditions explicitly and reject invalid inputs.
- Keep public collections immutable or defensively copied.
- Every algorithm change requires unit tests, including disconnected or otherwise
  non-ideal inputs.

## Teaching boundaries

- Student-facing materials should pose questions and provide scaffolding without
  giving away every conclusion.
- Instructor-facing notes may identify misconceptions, facilitation choices, and
  assessment criteria.
- Accessibility and collaboration expectations belong in the activity design,
  not only in a general statement.

## Publication boundary

This repository is a public, MIT-licensed independent portfolio project. Do not
describe it as a contribution accepted by an external project. Review changes
locally and keep the public `main` branch in a verified state.
