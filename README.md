# espresso-machine
I have been working integrating software since I got my first developer job; since then I started to integrate and customize
software for financial institions, after a considerable amount of time, I started to work for ATM vendors, then after
years of implementing client/server software for ATMs, I decided to implement a "State Machine" just for fun,
I'm rewriting all my previous SCXML experiments/java projects and re-structuring modules [I want to be fluent in scala and osgi got my interest]

Since I have been working with automated teller machine (ATM) software for long ago, the idea of implementing a state machine software to make ATMs flows is implicit, so I went to the web and found the SCXML spec, it comes as a submodule of
the VoiceML, but it makes perfect sense to implement ATM flows by just editing plain XML with a well known notation.
I experimented with Apache's SCXML implementation first, my first try with SCXML specs on its early stages was to integrate it with some propietary software that acts as an upper level abstracton of the j/XFS standard; Apache's SCXML was fine, it worked well, small and open source
but very disapointing the lack of development, the project looks dead by now, so why not to implement it by my own?
I finally will publish my work, I defenitely not a programming genius but maybe sharing is the way to get myself better. 

I am not any good at documenting,I just trying to explain the intention of the whole module; well in short,
a lazy SCXML implementation; Key features:

	- Laziness: I just want it to work with the less effort possible.
	- Embedable: Basically ... it is not completed, it is a bunch of classes that are useless, unless there are some other code making them work, I tried not to implement concrete
		bootstrap and enviromental specific classes, Yes! it is packaged as OSGi bundle  [just because I want this stuff running in an OSGi container],
		in theory that shouldn't affect any other target ecosystem.
